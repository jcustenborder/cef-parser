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

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class SeverityTest {

  @TestFactory
  public Stream<DynamicTest> parse() {
    Map<String, Severity> tests = new LinkedHashMap<>();
    tests.put("0", Severity.Low);
    tests.put("1", Severity.Low);
    tests.put("2", Severity.Low);
    tests.put("3", Severity.Low);
    tests.put("4", Severity.Medium);
    tests.put("5", Severity.Medium);
    tests.put("6", Severity.Medium);
    tests.put("7", Severity.High);
    tests.put("8", Severity.High);
    tests.put("9", Severity.VeryHigh);
    tests.put("10", Severity.VeryHigh);
    tests.put("Low", Severity.Low);
    tests.put("Medium", Severity.Medium);
    tests.put("High", Severity.High);
    tests.put("VeryHigh", Severity.VeryHigh);
    tests.put("ZZINVALID", Severity.Unknown);

    return tests.entrySet().stream().map(entry -> dynamicTest(entry.getKey(), () -> {
          final Severity actual = Severity.parse(entry.getKey());
          assertEquals(entry.getValue(), actual);
        })
    );
  }
}
