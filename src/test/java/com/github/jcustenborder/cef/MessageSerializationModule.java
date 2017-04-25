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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessageSerializationModule extends SimpleModule {
  public MessageSerializationModule() {
    super();
    addSerializer(Message.class, new Serializer());
    addDeserializer(Message.class, new Deserializer());
  }

  public static class Storage {
    public Date timestamp;
    public String host;
    public int cefVersion;
    public String deviceVendor;
    public String deviceProduct;
    public String deviceVersion;
    public String deviceEventClassId;
    public String name;
    public String severity;
    public Map<String, String> extensions;

    Message build() {
      Message message = mock(Message.class);
      when(message.timestamp()).thenReturn(this.timestamp);
      when(message.host()).thenReturn(this.host);
      when(message.cefVersion()).thenReturn(this.cefVersion);
      when(message.deviceVendor()).thenReturn(this.deviceVendor);
      when(message.deviceProduct()).thenReturn(this.deviceProduct);
      when(message.deviceVersion()).thenReturn(this.deviceVersion);
      when(message.deviceEventClassId()).thenReturn(this.deviceEventClassId);
      when(message.name()).thenReturn(this.name);
      when(message.severity()).thenReturn(this.severity);
      when(message.extensions()).thenReturn(this.extensions);
      return message;
    }
  }

  static class Serializer extends JsonSerializer<Message> {
    @Override
    public void serialize(Message message, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
      Storage storage = new Storage();
      storage.timestamp = message.timestamp();
      storage.host = message.host();
      storage.cefVersion = message.cefVersion();
      storage.deviceVendor = message.deviceVendor();
      storage.deviceProduct = message.deviceProduct();
      storage.deviceVersion = message.deviceVersion();
      storage.deviceEventClassId = message.deviceEventClassId();
      storage.name = message.name();
      storage.severity = message.severity();
      storage.extensions = message.extensions();
      jsonGenerator.writeObject(storage);
    }
  }

  static class Deserializer extends JsonDeserializer<Message> {

    @Override
    public Message deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
      Storage storage = jsonParser.readValueAs(Storage.class);
      return storage.build();
    }
  }
}
