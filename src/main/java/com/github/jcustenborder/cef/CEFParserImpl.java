/**
 * Copyright © 2017 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.cef;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CEFParserImpl implements CEFParser {
  final static TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");
  private static final Logger log = LoggerFactory.getLogger(CEFParserImpl.class);
  private static final Pattern PATTERN_CEF_PREFIX = Pattern.compile("^((?<timestamp>.+)\\s+(?<host>\\S+)\\s+)(?<cs0>CEF:\\d+)|^(?<cs1>CEF:\\d+)");
  private static final Pattern PATTERN_CEF_MAIN = Pattern.compile("(?<!\\\\)\\|");
  private static final Pattern PATTERN_EXTENSION = Pattern.compile("(\\w+)=");
  private static final List<String> DATE_FORMATS = Arrays.asList(
      "MMM dd yyyy HH:mm:ss.SSS zzz",
      "MMM dd yyyy HH:mm:ss.SSS",
      "MMM dd yyyy HH:mm:ss zzz",
      "MMM dd yyyy HH:mm:ss",
      "MMM dd HH:mm:ss.SSS zzz",
      "MMM dd HH:mm:ss.SSS",
      "MMM dd HH:mm:ss zzz",
      "MMM dd HH:mm:ss"
  );
  final MessageFactory messageFactory;

  public CEFParserImpl(MessageFactory messageFactory) {
    this.messageFactory = messageFactory;
  }


  @Override
  public Message parse(final String event) {
    log.trace("parse('{}')", event);
    Matcher prefixMatcher = PATTERN_CEF_PREFIX.matcher(event);
    if (!prefixMatcher.find()) {
      log.trace("parse() - event does not match pattern '{}'.", PATTERN_CEF_PREFIX.pattern());
      return null;
    }
    final String timestampText = prefixMatcher.group("timestamp");
    final String host = prefixMatcher.group("host");
    log.trace("parse() - timestampText = '{}' host='{}'", timestampText, host);
    Message.Builder builder = this.messageFactory.newBuilder();

    final int cefstartIndex;
    if (timestampText != null && !timestampText.isEmpty() && host != null && !host.isEmpty()) {
      Long longTimestamp = null;
      try {
        longTimestamp = Long.parseLong(timestampText);
      } catch (NumberFormatException e) {
        log.trace("Unable to parse timestamp '{}'", timestampText);
      }
      Date timestamp = null;
      if (null != longTimestamp) {
        log.trace("parse() - Detected timestamp is stored as a long.");
        timestamp = new Date(longTimestamp);
      } else {
        log.trace("parse() - Trying to parse the timestamp.");
        // SimpleDateFormat is not threadsafe so we have to create them each time.


        for (String df : DATE_FORMATS) {
          SimpleDateFormat dateFormat = new SimpleDateFormat(df);
          dateFormat.setTimeZone(TIME_ZONE);
          try {
            log.trace("parse() - Trying to parse '{}' with format '{}'", timestampText, df);
            timestamp = dateFormat.parse(timestampText);
            final boolean alterYear = !df.contains("yyyy");

            if (alterYear) {
              log.trace("parse() - date format '{}' does not specify the year. Might need to alter the year.", df);
              Calendar calendar = Calendar.getInstance(TIME_ZONE);
              int thisYear = calendar.get(Calendar.YEAR);
              calendar.setTime(timestamp);
              final int year = calendar.get(Calendar.YEAR);
              if (1970 == year) {
                log.trace("parse() - altering year from {} to {}", year, thisYear);
                calendar.set(Calendar.YEAR, thisYear);
                timestamp = calendar.getTime();
              }
            }

            break;
          } catch (ParseException e) {
            log.trace("parse() - Could not parse '{}' with '{}'.", timestampText, df);
          }
        }
        if (null == timestamp) {
          throw new IllegalStateException("Could not parse timestamp. '" + timestampText + "'");
        }
      }
      log.trace("parse() - timestamp = {}, {}", timestamp.getTime(), timestamp);
      builder.timestamp(timestamp);
      builder.host(host);

      cefstartIndex = prefixMatcher.start("cs0");
    } else {
      cefstartIndex = prefixMatcher.start("cs1");
    }

    log.trace("parse() - cefstartIndex = {}", cefstartIndex);
    final String eventBody = event.substring(cefstartIndex);

    List<String> parts = Arrays.asList(PATTERN_CEF_MAIN.split(eventBody));
    if (log.isTraceEnabled()) {
      int i = 0;
      for (String part : parts) {
        log.trace("parse() - parts[{}] = '{}'", i, part);
        i++;
      }
    }

    int index = 0;


    for (String token : parts) {
      token = token.replace("\\|", "|");
      log.trace("parse() - index={}, token='{}'", index, token);

      switch (index) {
        case 0:
          assert (token.startsWith("CEF:"));
          builder.cefVersion(Integer.parseInt(token.substring(4)));
          break;
        case 1:
          builder.deviceVendor(token);
          break;
        case 2:
          builder.deviceProduct(token);
          break;
        case 3:
          builder.deviceVersion(token);
          break;
        case 4:
          builder.deviceEventClassId(token);
          break;
        case 5:
          builder.name(token);
          break;
        case 6:
          builder.severity(token);
          break;
        default:
          break;
      }

      index++;
    }

    //No Extensions
    if (parts.size() == 7) {
      return builder.build();
    }

    final List<String> extensionParts = parts.subList(7, parts.size());
    final String extension = String.join("|", extensionParts)
        .replace("\\n", "\n")
        .replace("\\=", "=");
    log.trace("parse() - extension = '{}'", extension);
    Map<String, String> extensions = new LinkedHashMap<>(100);
    Matcher matcher = PATTERN_EXTENSION.matcher(extension);

    String key = null;
    String value = null;
    int lastEnd = -1, lastStart = -1;

    while (matcher.find()) {
      log.trace("parse() - matcher.start() = {}, matcher.end() = {}", matcher.start(), matcher.end());

      if (lastEnd > -1) {
        value = extension.substring(lastEnd, matcher.start()).trim();
        extensions.put(key, value);
        log.trace("parse() - key='{}' value='{}'", key, value);
      }

      key = matcher.group(1);
      lastStart = matcher.start();
      lastEnd = matcher.end();
    }

    if (lastStart > -1 && !extensions.containsKey(key)) {
      value = extension.substring(lastEnd).trim();
      extensions.put(key, value);
      log.trace("parse() - key='{}' value='{}'", key, value);
    }

    builder.extensions(extensions);
    return builder.build();
  }


}
