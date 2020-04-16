# Android project

## Build APK file

To build a debug APK, execute this:

    ./gradlew assembleDebug

The APK file will then be available at:

    app/build/outputs/apk/debug/app-debug.apk

In order to build a release APK, make sure to have signing set up.

If you do not have a signing key yet, create one as described here:
https://developer.android.com/studio/publish/app-signing#generate-key

Create file `secure.properties` with these contents:

    storeFile=/path/to/your/keystores/release.keystore
    storePassword=your.store.password
    keyAlias=your.key.alias
    keyPassword=your.key.password

Then you can execute this:

    ./gradlew assembleRelease

Then the APK is available here:

    app/build/outputs/apk/release/app-release.apk

You can install the APK files using adb like this:

    adb install -r app/build/outputs/apk/debug/app-debug.apk
    adb install -r app/build/outputs/apk/release/app-release.apk
