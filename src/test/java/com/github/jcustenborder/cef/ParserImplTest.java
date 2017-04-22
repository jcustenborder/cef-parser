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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static com.github.jcustenborder.cef.MessageAssertions.assertMessage;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class ParserImplTest {

  Parser parser;

  @BeforeEach
  public void before() {
    this.parser = new ParserImpl();
  }

  @TestFactory
  public Stream<DynamicTest> parse() throws IOException {
    List<TestCase> testCases = TestDataUtils.loadJsonResourceFiles(this.getClass().getPackage().getName() + ".messages", TestCase.class);

    return testCases.stream().map(testCase -> dynamicTest(testCase.testName(), () -> {
      Message actual = this.parser.parse(testCase.input);
      assertMessage(testCase.expected, actual);
    }));
  }

}
