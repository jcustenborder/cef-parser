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

import java.util.Locale;
import java.util.TimeZone;

/**
 * Interface is used to implement a CEFParser. Mainly for testing purposes.
 */
public interface CEFParser {
  /**
   * Method is used to parse text from a CEF message and convert it to a Message object.
   * Timestamps are parsed with timezone UTC and the root locale (C).
   * @param input CEF formatted string
   * @return
   */
  Message parse(final String input);

  /**
   * Method is used to parse text from a CEF message and convert it to a Message object.
   * @param input CEF formatted string
   * @param timeZone Timezone for the CEF timestamp if none was given
   * @param locale Locale used for parsing the CEF timestamp
   * @return
   */
  Message parse(final String input, final TimeZone timeZone, final Locale locale);
}
