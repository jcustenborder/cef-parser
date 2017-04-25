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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.jcustenborder.cef.MessageAssertions.assertMessage;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.Mockito.when;

public class ParserImplTest {

  CEFParser parser;

  @BeforeEach
  public void before() {
    this.parser = CEFParserFactory.create();
  }

  @TestFactory
  public Stream<DynamicTest> parse() throws IOException {
    List<TestCase> testCases = TestDataUtils.loadJsonResourceFiles(this.getClass().getPackage().getName() + ".messages", TestCase.class);

    return testCases.stream().map(testCase -> dynamicTest(testCase.testName(), () -> {
      Message actual = this.parser.parse(testCase.input);
      assertMessage(testCase.expected, actual);
    }));
  }

  @Disabled
  @Test
  public void generateDateFormatTestData() throws IOException, CloneNotSupportedException {
    List<TestCase> testCases = TestDataUtils.loadJsonResourceFiles(this.getClass().getPackage().getName() + ".messages", TestCase.class);
    Map<Integer, TestCase> testcaseByNumber = testCases.stream().filter(new Predicate<TestCase>() {
      @Override
      public boolean test(TestCase testCase) {
        return testCase.testNumber < 1000;
      }
    }).collect(Collectors.toMap(i -> i.testNumber, i -> i));

    //Filter out milliseconds since most of these formats don't go that far.
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    calendar.set(Calendar.MILLISECOND, 0);

    Date date = calendar.getTime();
    final String testInputformat = "%s hostname.example.com %s";
    final String testFileNameFormat = "Message%04d.json";
    final File outputRoot = new File("src/test/resources/com/github/jcustenborder/cef/messages");

    List<String> dateFormats = Arrays.asList(
        "MMM dd HH:mm:ss.SSS zzz",
        "MMM dd HH:mm:ss.SSS",
        "MMM dd HH:mm:ss zzz",
        "MMM dd HH:mm:ss",
        "MMM dd yyyy HH:mm:ss.SSS zzz",
        "MMM dd yyyy HH:mm:ss.SSS",
        "MMM dd yyyy HH:mm:ss zzz",
        "MMM dd yyyy HH:mm:ss"
    );

    for (Map.Entry<Integer, TestCase> kvp : testcaseByNumber.entrySet()) {
      int testNumber = kvp.getKey();
      testNumber += 1000;

      String testFileName = String.format(testFileNameFormat, testNumber);
      File outputPath = new File(outputRoot, testFileName);
      TestCase testCase = (TestCase) kvp.getValue().clone();
      when(testCase.expected.timestamp()).thenReturn(date);
      when(testCase.expected.host()).thenReturn("hostname.example.com");
      testCase.input = String.format(testInputformat, date.getTime(), testCase.input);

      ObjectMapperFactory.INSTANCE.writeValue(outputPath, testCase);

      for (String f : dateFormats) {
        DateFormat dateFormat = new SimpleDateFormat(f);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        testNumber += 1000;

        testFileName = String.format(testFileNameFormat, testNumber);
        outputPath = new File(outputRoot, testFileName);
        testCase = (TestCase) kvp.getValue().clone();
        testCase.input = String.format(testInputformat, dateFormat.format(date), testCase.input);
        ObjectMapperFactory.INSTANCE.writeValue(outputPath, testCase);
      }

    }

  }

}
