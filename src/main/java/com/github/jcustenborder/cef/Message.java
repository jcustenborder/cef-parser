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

/**
 * This interface is a parsed representation of a CEF message.
 */
public interface Message {
  /**
   * Timestamp for the message
   * @return Timestamp for the message. Can be null if the message did not have a timestamp and host.
   */
  Date timestamp();

  /**
   * Host for the message.
   * @return Host for the message. Can be null if the message did not have a timestamp and host.
   */
  String host();

  /**
   * Version of CEF the message is using.
   * @return Version of CEF the message is using.
   */
  int cefVersion();

  /**
   * Vendor of the device that logged the message.
   * @return Vendor of the device that logged the message.
   */
  String deviceVendor();

  /**
   * The product that logged the message.
   * @return The product that logged the message.
   */
  String deviceProduct();

  /**
   * The version of the device that is logging the message.
   * @return The version of the device that is logging the message.
   */
  String deviceVersion();

  /**
   * The internal event id for the message.
   * @return The internal event id for the message.
   */
  String deviceEventClassId();

  /**
   * Name of the event. This is typically a short description.
   * @return - Name of the event. This is typically a short description.
   */
  String name();

  /**
   * The severity of the message.
   * @return The severity of the message.
   */
  String severity();

  /**
   * Key value pairs of any extensions to the message.
   * @return Key value pairs of any extensions to the message.
   */
  Map<String, String> extensions();

  /**
   * The Builder used to create the message object.
   */
  interface Builder {

    /**
     * Sets the timestamp for the message
     * @param timestamp
     * @return current builder.
     */
    Builder timestamp(Date timestamp);

    /**
     * Sets the host for the message
     * @param host
     * @return current builder.
     */
    Builder host(String host);

    /**
     * Sets the CEF version for the message.
     * @param cefVersion
     * @return current builder.
     */
    Builder cefVersion(int cefVersion);

    /**
     * Sets the deviceVendor for the message.
     * @param deviceVendor
     * @return current builder.
     */
    Builder deviceVendor(String deviceVendor);

    /**
     * Sets the deviceProduct for the message.
     * @param deviceProduct
     * @return current builder.
     */
    Builder deviceProduct(String deviceProduct);

    /**
     * Sets the deviceVersion for the message.
     * @param deviceVersion
     * @return current builder.
     */
    Builder deviceVersion(String deviceVersion);

    /**
     * Sets the deviceEventClassId for the message.
     * @param deviceEventClassId
     * @return current builder.
     */
    Builder deviceEventClassId(String deviceEventClassId);

    /**
     * Sets the name for the message.
     * @param name
     * @return current builder.
     */
    Builder name(String name);

    /**
     * Sets the severity for the message.
     * @param severity
     * @return current builder.
     */
    Builder severity(String severity);

    /**
     * Sets the extensions for the message.
     * @param extensions
     * @return current builder.
     */
    Builder extensions(Map<String, String> extensions);

    /**
     * Timestamp for the message
     * @return Timestamp for the message. Can be null if the message did not have a timestamp and host.
     */
    Date timestamp();

    /**
     * Host for the message.
     * @return Host for the message. Can be null if the message did not have a timestamp and host.
     */
    String host();

    /**
     * Version of CEF the message is using.
     * @return Version of CEF the message is using.
     */
    int cefVersion();

    /**
     * Vendor of the device that logged the message.
     * @return Vendor of the device that logged the message.
     */
    String deviceVendor();

    /**
     * The product that logged the message.
     * @return The product that logged the message.
     */
    String deviceProduct();

    /**
     * The version of the device that is logging the message.
     * @return The version of the device that is logging the message.
     */
    String deviceVersion();

    /**
     * The internal event id for the message.
     * @return The internal event id for the message.
     */
    String deviceEventClassId();

    /**
     * Name of the event. This is typically a short description.
     * @return - Name of the event. This is typically a short description.
     */
    String name();

    /**
     * The severity of the message.
     * @return The severity of the message.
     */
    String severity();

    /**
     * Key value pairs of any extensions to the message.
     * @return Key value pairs of any extensions to the message.
     */
    Map<String, String> extensions();

    /**
     * Method is used to build the representing message.
     * @return Populated message.
     */
    Message build();
  }
}
