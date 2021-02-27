[![](https://jitpack.io/v/khodealib/ObserverScrollView.svg)](https://jitpack.io/#khodealib/ObserverScrollView)
[![API](https://img.shields.io/badge/API-9%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=9)
# ObserverScrollView
Android library to observe scroll events on scrollable views.
It's easy to interact with the Toolbar introduced in Android 5.0 Lollipop and may be helpful to implement look and feel of Material Design apps.

![](https://raw.githubusercontent.com/ksoichiro/Android-ObservableScrollView/master/samples/images/demo12.gif)
![](https://raw.githubusercontent.com/ksoichiro/Android-ObservableScrollView/master/samples/images/demo10.gif)
![](https://raw.githubusercontent.com/ksoichiro/Android-ObservableScrollView/master/samples/images/demo11.gif)
![](https://raw.githubusercontent.com/ksoichiro/Android-ObservableScrollView/master/samples/images/demo13.gif)

![](https://raw.githubusercontent.com/ksoichiro/Android-ObservableScrollView/master/samples/images/demo1.gif)
![](https://raw.githubusercontent.com/ksoichiro/Android-ObservableScrollView/master/samples/images/demo2.gif)
![](https://raw.githubusercontent.com/ksoichiro/Android-ObservableScrollView/master/samples/images/demo3.gif)
![](https://raw.githubusercontent.com/ksoichiro/Android-ObservableScrollView/master/samples/images/demo4.gif)

![](https://raw.githubusercontent.com/ksoichiro/Android-ObservableScrollView/master/samples/images/demo5.gif)
![](https://raw.githubusercontent.com/ksoichiro/Android-ObservableScrollView/master/samples/images/demo6.gif)
![](https://raw.githubusercontent.com/ksoichiro/Android-ObservableScrollView/master/samples/images/demo7.gif)
![](https://raw.githubusercontent.com/ksoichiro/Android-ObservableScrollView/master/samples/images/demo8.gif)


# Dependency
Top level build.gradle
```groovy
allprojects {
   repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Module level build.gradle
```groovy
dependencies {
  implementation "com.github.khodealib:ObserverScrollView:$version"
}
```