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

import java.util.HashMap;
import java.util.Map;

public enum Severity {
  Unknown,
  Low,
  Medium,
  High,
  VeryHigh;

  private static final Map<String, Severity> LOOKUP;

  static {
    Map<String, Severity> lookup = new HashMap<>();
    lookup.put("0", Severity.Low);
    lookup.put("1", Severity.Low);
    lookup.put("2", Severity.Low);
    lookup.put("3", Severity.Low);
    lookup.put("4", Severity.Medium);
    lookup.put("5", Severity.Medium);
    lookup.put("6", Severity.Medium);
    lookup.put("7", Severity.High);
    lookup.put("8", Severity.High);
    lookup.put("9", Severity.VeryHigh);
    lookup.put("10", Severity.VeryHigh);
    lookup.put("Low", Severity.Low);
    lookup.put("Medium", Severity.Medium);
    lookup.put("High", Severity.High);
    lookup.put("VeryHigh", Severity.VeryHigh);
    LOOKUP = lookup;
  }

  public static Severity parse(String text) {
    return LOOKUP.getOrDefault(text, Severity.Unknown);
  }
}
