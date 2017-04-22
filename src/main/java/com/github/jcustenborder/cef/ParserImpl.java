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

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

class ParserImpl implements Parser {
  private static final Logger log = LoggerFactory.getLogger(ParserImpl.class);


  @Override
  public Message parse(final String input) throws IOException {
    log.trace("parser('{}')", input);

    CSVParser csvParser =
        new CSVParserBuilder()
            .withSeparator('|')
            .withEscapeChar('\\')
            .build();

    String[] parts = csvParser.parseLine(input);
    log.trace("parts = {}", parts);
    //TODO: Check that length is either 7 or 8.
    final String deviceVendor = parts[1];
    final String deviceProduct = parts[2];
    final String deviceVersion = parts[3];
    final String deviceEventClassId = parts[4];
    final String name = parts[5];
    final int severity = Integer.parseInt(parts[6]);
    final String extensionText = parts.length == 8 ? parts[7] : "";



    return null;
  }


}
