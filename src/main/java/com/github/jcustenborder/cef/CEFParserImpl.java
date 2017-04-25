/**
 * Copyright © 2017 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.cef;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CEFParserImpl implements CEFParser {
  private static final Logger log = LoggerFactory.getLogger(CEFParserImpl.class);
  private static final Pattern PATTERN_CEF_MAIN = Pattern.compile("(?<!\\\\)\\|");
  private static final Pattern PATTERN_EXTENSION = Pattern.compile("(\\w+)=");
  final MessageFactory messageFactory;

  public CEFParserImpl(MessageFactory messageFactory) {
    this.messageFactory = messageFactory;
  }

  @Override
  public Message parse(final String event) throws IOException {
    log.trace("parse('{}')", event);
    List<String> parts = Splitter.on(PATTERN_CEF_MAIN).splitToList(event);
    if (log.isTraceEnabled()) {
      int i = 0;
      for (String part : parts) {
        log.trace("parse() - parts[{}] = '{}'", i, part);
        i++;
      }
    }

    int index = 0;

    Message.Builder builder = this.messageFactory.newBuilder();

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
    final String extension = Joiner.on('|').join(extensionParts)
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
