# Tests on using [dsprenkels/sss](https://github.com/dsprenkels/sss)

There are some tests on just using the library in C in [c](c).

Also there's going to be some code on running the whole thing
using JNA in [java](java):
* [Getting Started](java/getting-started): a minimal example on using JNA as
  explained here: https://github.com/java-native-access/jna/blob/master/www/GettingStarted.md
* [Sandbox](java/sandbox): a sandbox for testing JNA functionality

The next step is to get everything running from within Android apps.
There's an [Android testing project](android) for that.

There's also [an attempt](renjin/java) to compile the C library to JVM bytecode using
[Renjin](https://github.com/bedatadriven/renjin)'s [GCC-Bridge](https://github.com/bedatadriven/renjin/tree/master/tools/gcc-bridge).
