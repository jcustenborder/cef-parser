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

import java.util.Date;

/**
 * This interface attempts to parse a string containing a date and time.
 */
public interface DateParser {

  /**
   * Attempts to parse supplied <code>dateTime</code> string.
   * @param timestampText Text representing a valid timestamp
   * @return The parsed date or <code>null</code> on failure.
   */
  Date parseDate(String timestampText);

}
