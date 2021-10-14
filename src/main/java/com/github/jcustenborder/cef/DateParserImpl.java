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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.google.common.primitives.Longs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateParserImpl implements DateParser {

  private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");
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
  private static final Logger log = LoggerFactory.getLogger(DateParserImpl.class);

  @Override
  public Date parseDate(String timestampText) {
    Long longTimestamp = Longs.tryParse(timestampText);
    if (null != longTimestamp) {
      log.trace("parse() - Detected timestamp is stored as a long.");
      return new Date(longTimestamp);
    }

    log.trace("parse() - Trying to parse the timestamp.");
    // SimpleDateFormat is not threadsafe so we have to create a new one each time.

    for (String pattern : DATE_FORMATS) {
      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
      dateFormat.setTimeZone(TIME_ZONE);
      try {
        log.trace("parse() - Trying to parse '{}' with format '{}'", timestampText, pattern);
        Date timestamp = dateFormat.parse(timestampText);
        final boolean alterYear = !pattern.contains("yyyy");

        if (alterYear) {
          log.trace("parse() - date format '{}' does not specify the year. Might need to alter the year.", pattern);
          Calendar calendar = Calendar.getInstance(TIME_ZONE);
          int thisYear = calendar.get(Calendar.YEAR);
          calendar.setTime(timestamp);
          final int year = calendar.get(Calendar.YEAR);
          if (1970 == year) {
            log.trace("parse() - altering year from {} to {}", year, thisYear);
            calendar.set(Calendar.YEAR, thisYear);
            return calendar.getTime();
          }
        }

        return timestamp;
      } catch (ParseException e) {
        log.trace("parse() - Could not parse '{}' with '{}'.", timestampText, pattern);
      }
    }

    return null;
  }
}