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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCase implements NamedTest, Cloneable {
  static final Pattern PATTERN_NUMBER = Pattern.compile("\\d+$");
  public String input;
  public Message expected;
  @JsonIgnore
  public String testName;
  @JsonIgnore
  public Integer testNumber;

  @JsonIgnore
  @Override
  public String testName() {
    return this.testName;
  }

  @JsonIgnore
  @Override
  public void testName(String testName) {
    this.testName = testName;
    Matcher matcher = PATTERN_NUMBER.matcher(testName);
    Preconditions.checkState(matcher.find(), "Should match regex. %s", PATTERN_NUMBER.pattern());
    this.testNumber = Integer.parseInt(matcher.group(0));
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    TestCase clone = new TestCase();
    clone.testNumber = this.testNumber;
    clone.testName = this.testName;
    clone.expected = this.expected;
    clone.input = this.input;
    return clone;
  }
}
