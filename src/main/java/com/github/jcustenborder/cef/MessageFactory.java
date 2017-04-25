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

/**
 * This interface allows you to create your own implementation of a message factory. A good use case would be if
 * you wanted to use a different internal storage rather than copy each one of the fields manually. Message.Builder
 * objects do not need to be threadsafe as they are only accessed in the creating thread.
 */
public interface MessageFactory {
  /**
   * Called each time a new message is processed.
   * @return A new builder for the message.
   */
  Message.Builder newBuilder();
}
