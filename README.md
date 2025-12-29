## Vlog

> Vlog was featured in [Android Weekly](https://androidweekly.net/issues/issue-442)

An In-Display real-time logging Library for Android. <br> 
Vlog provides an easy and convenient way to access logs right on your phone.

[![](https://jitpack.io/v/girish3/Vlog.svg)](https://jitpack.io/#girish3/Vlog)
<img src="https://img.shields.io/github/license/girish3/vlog"/>
<img src="https://img.shields.io/badge/API-19%2B-blue"/>
<img src="https://img.shields.io/badge/Kotlin-100%25-green"/>

<img src="/demo_resources/demo1.1.gif" width="32%"/>

Messenger chat like bubble is introduced to display logs in a non-intrusive manner. The bubble also shows the count badge to indicate the presence of logs. Along with that, basic filtering capability helps with finding specific logs,

<p>
<img src="/demo_resources/demo1.2.gif" width="32%"/>
<img src="/demo_resources/demo1.3.gif" width="32%"/>
</p>

## Philosophy
Logs play a crucial part in diagnosing issues. Without proper logging, developers may have to spend countless hours debugging the root cause. When the codebase becomes huge, logs can also prove to be useful in understanding complex execution flows. Although one can argue to follow execution from one breakpoint to another, the whole process can be time-consuming. But for the most part, logs are an indispensable tool in any developer’s arsenal. More so for an android developer where there can be network failures, device permission denials, SQL errors, background thread issues, telemetry data inaccuracies, and innumerable other concerns. And Vlog takes it a step forward and helps in conveniently check logs right on your phone.
 
## Getting Started

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
Add the library dependency in `build.gradle` file. Notice the `no-op` version, an empty implementation, which ensures library is not added to the release builds:
```groovy
dependencies {
 debugImplementation 'com.github.girish3.Vlog:library:v0.3'
 releaseImplementation 'com.github.girish3.Vlog:library-no-op:v0.3'
}
```

### Basic Usage
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

### Integrate with Timber
[Timber](https://github.com/JakeWharton/timber) is a popular logging library. Timber is extensible therefore Vlog can be seamlessly integrated alongside Timber. Add a behavior through `Tree` instance and plant the instance by calling `Timber.plant`. For more details, check the [timber sample module](https://github.com/girish3/Vlog/tree/master/timber-sample/src/main/java/com/example/timber) in this repository.

### Advance Usage
Often, there is a need to not just print logs but also either to save them in a local file or upload it to a server. The [Chain of responsibility pattern](https://www.tutorialspoint.com/design_pattern/chain_of_responsibility_pattern.htm) is a recommended pattern for such needs. In this pattern, each object in the chain receives the log data and can therefore be responsible to print the log statement (logcat), save in a file, or, in our use-case, pass the data to **Vlog** library. Refer [sample app](https://github.com/girish3/Vlog/tree/master/app/src/main/java/com/girish/vlogsample) in this repo for more details. For a quick setup, the app's [logger folder](https://github.com/girish3/Vlog/tree/master/app/src/main/java/com/girish/vlogsample/logger) can directly be copy-pasted in any Android project.

<img src="/demo_resources/vlog_chain_of_responsibility.png" width = 60%/>

### Contribute
All types of contribution is accepted. Raise issues, suggest changes or submit a pull request.
