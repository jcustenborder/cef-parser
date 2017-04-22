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

import java.util.Map;

class MessageImpl implements Message {
  int cefVersion;
  String deviceVendor;
  String deviceProduct;
  String deviceVersion;
  String deviceEventClassId;
  String name;
  String severity;
  Map<String, String> extensions;

  @Override
  public int cefVersion() {
    return this.cefVersion;
  }

  @Override
  public String deviceVendor() {
    return this.deviceVendor;
  }

  @Override
  public String deviceProduct() {
    return this.deviceProduct;
  }

  @Override
  public String deviceVersion() {
    return this.deviceVersion;
  }

  @Override
  public String deviceEventClassId() {
    return this.deviceEventClassId;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public String severity() {
    return this.severity;
  }

  @Override
  public Map<String, String> extensions() {
    return this.extensions;
  }
}
