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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperFactory {
  public static final ObjectMapper INSTANCE;

  static {
    INSTANCE = new ObjectMapper();
    INSTANCE.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    INSTANCE.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
    INSTANCE.configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
    INSTANCE.configure(SerializationFeature.INDENT_OUTPUT, true);

    INSTANCE.registerModule(new MessageSerializationModule());
  }

}