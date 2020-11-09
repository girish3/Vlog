<h1 align="center">Vlog</h1></br>

<p align="center"> 
An In-Display logging Library for Android. <br> 
Vlog provides an easy and convenient way to access logs, that too, without needing a Mac or a PC.
</p>

<img src="/demo_resources/vlog_demo.gif" width="32%"/>

 
## Integration Steps

### Download
Add, as shown below, the library dependency in your `build.gradle` file,
```groovy
implementation 'com.girish.vlog:0.1.0'
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
