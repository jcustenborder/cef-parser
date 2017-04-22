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

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParserImplTest {


  @Test
  public void foo() throws IOException {
    final String input = "CEF:0|ArcSight|ArcSight|6.0.3.6664.0|agent:030|Agent [test] type [testalertng] started|Low| " +
        "eventId=1 mrt=1396328238973 categorySignificance=/Normal categoryBehavior=/Execute/Start " +
        "categoryDeviceGroup=/Application catdt=Security Mangement categoryOutcome=/Success " +
        "categoryObject=/Host/Application/Service art=1396328241038 cat=/Agent/Started " +
        "deviceSeverity=Warning rt=1396328238937 fileType=Agent " +
        "cs2=<Resource ID\\=\"3DxKlG0UBABCAA0cXXAZIwA\\=\\=\"/> c6a4=fe80:0:0:0:495d:cc3c:db1a:de71 " +
        "cs2Label=Configuration Resource c6a4Label=Agent " +
        "IPv6 Address ahost=SKEELES10 agt=888.99.100.1 agentZoneURI=/All Zones/ArcSight " +
        "System/Private Address Space " +
        "Zones/RFC1918: 888.99.0.0-888.200.255.255 av=6.0.3.6664.0 atz=Australia/Sydney " +
        "aid=3DxKlG0UBABCAA0cXXAZIwA\\=\\= at=testalertng dvchost=SKEELES10 dvc=888.99.100.1 " +
        "deviceZoneURI=/All Zones/ArcSight System/Private Address Space Zones/RFC1918: " +
        "888.99.0.0-888.200.255.255 dtz=Australia/Sydney _cefVer=0.1";

    Parser parserImpl = new ParserImpl();

    Message message = parserImpl.parse(input);
    assertNotNull(message);
  }

  @Test
  public void foo2() throws IOException {
    final String input = "CEF:0|Security|threatmanager|1.0|100|worm successfully stopped|10|src=10.0.0.1 dst=2.1.2.2 spt=1232";

    Parser parser = new ParserImpl();

    Message message = parser.parse(input);
    assertNotNull(message);
  }
}
