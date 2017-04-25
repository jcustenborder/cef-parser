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

import java.util.HashMap;
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

  public static class BuilderImpl implements Message.Builder {
    int cefVersion;
    String deviceVendor;
    String deviceProduct;
    String deviceVersion;
    String deviceEventClassId;
    String name;
    String severity;
    Map<String, String> extensions = new HashMap<>();

    @Override
    public int cefVersion() {
      return cefVersion;
    }

    @Override
    public Builder cefVersion(int cefVersion) {
      this.cefVersion = cefVersion;
      return this;
    }

    @Override
    public String deviceVendor() {
      return deviceVendor;
    }

    @Override
    public Builder deviceVendor(String deviceVendor) {
      this.deviceVendor = deviceVendor;
      return this;
    }

    @Override
    public String deviceProduct() {
      return deviceProduct;
    }

    @Override
    public Builder deviceProduct(String deviceProduct) {
      this.deviceProduct = deviceProduct;
      return this;
    }

    @Override
    public String deviceVersion() {
      return deviceVersion;
    }

    @Override
    public Builder deviceVersion(String deviceVersion) {
      this.deviceVersion = deviceVersion;
      return this;
    }

    @Override
    public String deviceEventClassId() {
      return deviceEventClassId;
    }

    @Override
    public Builder deviceEventClassId(String deviceEventClassId) {
      this.deviceEventClassId = deviceEventClassId;
      return this;
    }

    @Override
    public String name() {
      return name;
    }

    @Override
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    @Override
    public String severity() {
      return severity;
    }

    @Override
    public Builder severity(String severity) {
      this.severity = severity;
      return this;
    }

    @Override
    public Map<String, String> extensions() {
      return extensions;
    }

    @Override
    public Builder extensions(Map<String, String> extensions) {
      this.extensions = extensions;
      return this;
    }

    @Override
    public Message build() {
      MessageImpl message = new MessageImpl();
      message.cefVersion = this.cefVersion;
      message.deviceVendor = this.deviceVendor;
      message.deviceProduct = this.deviceProduct;
      message.deviceVersion = this.deviceVersion;
      message.deviceEventClassId = this.deviceEventClassId;
      message.name = this.name;
      message.severity = this.severity;
      message.extensions = this.extensions;

      return message;
    }
  }

}
