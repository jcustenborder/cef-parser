# Introduction

This library is used to parse the [ArcSight Common Event Format (CEF)](https://www.protect724.hpe.com/docs/DOC-1072). 
CEF is a logging protocol that is typically sent over syslog. Messages will be formatted similar to this:
 
 ```text
Sep 19 08:26:10 host CEF:0|security|threatmanager|1.0|100|detected a \| in message|10|src=10.0.0.1 act=blocked a | dst=1.1.1.1
Sep 19 08:26:10 host CEF:0|security|threatmanager|1.0|100|detected a \\ in packet|10|src=10.0.0.1 act=blocked a \\ dst=1.1.1.1
```

This is over the standard [Syslog](https://en.wikipedia.org/wiki/Syslog) protocol. Above there are three


