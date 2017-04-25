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

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TestCase implements NamedTest {
  public String input;
  public Message expected;
  @JsonIgnore
  public String testName;

  @JsonIgnore
  @Override
  public String testName() {
    return this.testName;
  }

  @JsonIgnore
  @Override
  public void testName(String testName) {
    this.testName = testName;
  }
}
