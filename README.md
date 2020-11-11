## Vlog
An In-Display real-time logging Library for Android. <br> 
Vlog provides an easy and convenient way to access logs right on your phone.

[![](https://jitpack.io/v/girish3/Vlog.svg)](https://jitpack.io/#girish3/Vlog)
<img src="https://img.shields.io/github/license/girish3/vlog"/>
<img src="https://img.shields.io/badge/API-19%2B-blue"/>
<img src="https://img.shields.io/badge/Kotlin-100%25-green"/>

## Screenshots

<p align="center">
<img src="/demo_resources/demo1.1.gif" width="32%"/>
<img src="/demo_resources/demo1.2.gif" width="32%"/>
<img src="/demo_resources/demo1.3.gif" width="32%"/>
</p>

As can be seen in above screenshots, messenger-like bubble is introduced to display logs in a non-intrusive manner. The bubble also shows the count badge to indicate the presence of logs. Along with that, basic filtering capability helps with finding specific logs.

## Philosophy
All investigation starts from logs. Whether its an app crash, server error or a login issues, we always check logs to understand the root cause. Quite often, we need to reproduce the issue ourselves and parallely look for logs on our Mac or a PC.  If only, we could conveniently look into the logs right on our phones. Well, wait no more, Vlog makes it possible to display logs real time while you interact with your app. There are plenty of other use cases where Vlog can prove to be very convenient,
- Every now and then, the issue is on the server side. In that case, quickly checking logs and reporting the issue directly to the concerned back-end team can save a lot of time.
- Verify user analytics logs (useful for product team)
- Debug on the go
 
## Integration Steps

### Gradle Dependency
[![](https://jitpack.io/v/girish3/Vlog.svg)](https://jitpack.io/#girish3/Vlog)  

Add maven source in root `build.gradle`:

```groovy
allprojects {
 repositories {
  ...
  maven { url 'https://jitpack.io' }
 }
}
```
Add the library dependency in module `build.gradle`:
```groovy
dependencies {
 implementation 'com.github.girish3:Vlog:v0.1.1'
}
```

### Basic
The Vlog exposes easy-to-use APIs and has same logging methods as Android's Log utility ( `Log.v`, `Log.d`...)
```kotlin
val vlog = Vlog.getInstance(context) 
// In the above code, make sure to pass application context as an argument instead of Activity context

// start vlogging (the bubble will appear)
vlog.start()

vlog.v("<tag>", "<log message>") // verbose log
vlog.e("<tag>", "<log message>") // error log

// stop vlogging
vlog.stop()

// check if vlog is currently active
vlog.isEnabled() // returns true if vlog.start() was called
```

### Advanced
Often, there is a need to not just print logs but also either to save them in a local file or upload it to a server. The [Chain of responsibility pattern](https://www.tutorialspoint.com/design_pattern/chain_of_responsibility_pattern.htm) is a recommended pattern for such needs. In this pattern, each object in the chain receives the log data and can therefore be responsible to print the log statement (logcat), save in a file, or, in our use-case, pass the data to **Vlog** library. Refer [sample app](https://github.com/girish3/Vlog/tree/master/app/src/main/java/com/girish/vlogsample) in this repo for more details. For a quick setup, the app's [logger folder](https://github.com/girish3/Vlog/tree/master/app/src/main/java/com/girish/vlogsample/logger) can directly be copy-pasted in any Android project.

<img src="/demo_resources/vlog_chain_of_responsibility.png" width = 60%/>
