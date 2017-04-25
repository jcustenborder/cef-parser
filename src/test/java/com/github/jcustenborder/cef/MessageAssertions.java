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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MessageAssertions {
  public static void assertMessage(Message expected, Message actual) {
    if (null == expected) {
      assertNull(actual, "actual should be null.");
    } else {
      assertNotNull(actual, "actual should not be null.");
    }

    assertEquals(expected.timestamp(), actual.timestamp(), "timestamp() does not match");
    assertEquals(expected.host(), actual.host(), "host() does not match");
    assertEquals(expected.cefVersion(), actual.cefVersion(), "cefVersion() does not match");
    assertEquals(expected.deviceVendor(), actual.deviceVendor(), "deviceVendor() does not match");
    assertEquals(expected.deviceProduct(), actual.deviceProduct(), "deviceProduct() does not match");
    assertEquals(expected.deviceVersion(), actual.deviceVersion(), "deviceVersion() does not match");
    assertEquals(expected.deviceEventClassId(), actual.deviceEventClassId(), "deviceEventClassId() does not match");
    assertEquals(expected.name(), actual.name(), "name() does not match");
    assertEquals(expected.severity(), actual.severity(), "severity() does not match");
    assertEquals(expected.extensions(), actual.extensions(), "extensions() does not match");
  }
}
