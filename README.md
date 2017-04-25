# Introduction

This library is used to parse the [ArcSight Common Event Format (CEF)](https://www.protect724.hpe.com/docs/DOC-1072). 
CEF is a logging protocol that is typically sent over syslog. Messages will be formatted similar to this:
 
 ```text
Sep 19 08:26:10 host CEF:0|security|threatmanager|1.0|100|detected a \| in message|10|src=10.0.0.1 act=blocked a | dst=1.1.1.1
Sep 19 08:26:10 host CEF:0|security|threatmanager|1.0|100|detected a \\ in packet|10|src=10.0.0.1 act=blocked a \\ dst=1.1.1.1
CEF:0|security|threatmanager|1.0|100|detected a \\ in packet|10|src=10.0.0.1 act=blocked a \\ dst=1.1.1.1
```

This is over the standard [Syslog](https://en.wikipedia.org/wiki/Syslog) protocol. A typical syslog message will include
the timestamp, host, and the message for the event. This library can parse entries that contain that have the timestamp and host,
or will also work if they are missing.

# Example

Below is a simple example of how to use the parser.

```java
import com.github.jcustenborder.cef.CEFParserFactory;
import com.github.jcustenborder.cef.CEFParser;
import com.github.jcustenborder.cef.Message;

class Foo {
  static void main(String... args) throws Exception {
    CEFParser f = CEFParserFactory.create();
    Message message = f.parse("Sep 19 08:26:10 host CEF:0|security|threatmanager|1.0|100|detected a \| in message|10|src=10.0.0.1 act=blocked a | dst=1.1.1.1");
  }
}
```


