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
import java.util.Map;

public interface Message {
  Date timestamp();

  String host();

  int cefVersion();

  String deviceVendor();

  String deviceProduct();

  String deviceVersion();

  String deviceEventClassId();

  String name();

  String severity();

  Map<String, String> extensions();

  interface Builder {

    Builder timestamp(Date timestamp);

    Builder host(String host);

    Builder cefVersion(int cefVersion);

    Builder deviceVendor(String deviceVendor);

    Builder deviceProduct(String deviceProduct);

    Builder deviceVersion(String deviceVersion);

    Builder deviceEventClassId(String deviceEventClassId);

    Builder name(String name);

    Builder severity(String severity);

    Builder extensions(Map<String, String> extensions);

    Date timestamp();

    String host();

    int cefVersion();

    String deviceVendor();

    String deviceProduct();

    String deviceVersion();

    String deviceEventClassId();

    String name();

    String severity();

    Map<String, String> extensions();

    Message build();
  }
}
