ImagePicker
========

A library that supports selecting images from the device library or directly from the camera.

[![](https://jitpack.io/v/nguyenhoanglam/ImagePicker.svg)](https://jitpack.io/#nguyenhoanglam/ImagePicker)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ImagePicker-green.svg?style=true)](https://android-arsenal.com/details/1/4072)

Screenshots
--------

<img src="https://i.imgur.com/ZM09aU3.png" height="652" width="350"> <img src="https://i.imgur.com/Hs9nhVt.png" height="652" width="350">

Installation
--------

Add the following maven repositories in root build.gradle
```java
allprojects {
    repositories {
        ...
        maven { url "https://maven.google.com" }
        maven { url "https://jitpack.io" }
    }
}
```

Add the following dependency in app build.gradle
```java
dependencies {
    implementation 'com.github.nguyenhoanglam:ImagePicker:1.4.0'
}
```

You NEED to migrate your project to support AndroidX by add following lines on gradle.properties file:
```java
android.useAndroidX=true
android.enableJetifier=true
```

Usage
--------

### Start ImagePicker
```java
ImagePicker.with(this)                                       
           .setFolderMode(true)                              
           .setFolderTitle("Album")                          
           .setDirectoryName("Image Picker")                 
           .setMultipleMode(true)                            
           .setShowNumberIndicator(true)
           .setMaxSize(5)
           .setLimitMessage("You can select up to 5 images")
           .setSelectedImages(images)
           .setRequestCode(100)
           .start();
```

### Handle selected images

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
        ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
        // do your logic here...
    }
    super.onActivityResult(requestCode, resultCode, data);  // You MUST have this line to be here
                                                            // so ImagePicker can work with fragment mode
}
```

### Methods's description

| Name | Description | Default
| --- | --- | --- |
| `with` | Initialize ImagePicker with activity or fragment context |
| `setStatusBarColor` | Status bar color, require API >= 21 | `#000000`
| `setToolbarColor` | Toolbar color | `#212121`
| `setToolbarTextColor` | Toolbar text color | `#FFFFFF`
| `setToolbarIconColor` | Toolbar icon color | `#FFFFFF`
| `setBackgroundColor` | Background color | `#212121`
| `setProgressBarColor` | ProgressBar color | `#4CAF50`
| `setIndicatorColor` | Selected image's indicator color | `#1976D2`
| `setCameraOnly` | Start camera and return captured image | `false`
| `setMultipleMode` | Allow to select multiple images | `true`
| `setFolderMode` | Group images by folders | `false`
| `setFolderTitle` | Folder screen's title, require FolderMode = `true` | `Albums`
| `setImageTitle` | Image screen's title, require FolderMode = `false` | `Galleries`
| `setDoneTitle` | Done button's title | `DONE`
| `setAlwaysShowDoneButton` | Show done button even though no image selected | `false`
| `setShowCamera` | Show camera button | `true`
| `setDirectoryName` | Folder name for captured images (located in DCIM folder) | `Camera`
| `setShowNumberIndicator` | Show selected image's indicator as number | `false`
| `setMaxSize` | Max images can be selected | `Int.MAX_VALUE`
| `setLimitMessage` | Folder screen's title |
| `setSelectedImages` | List of images that will be shown as selected in ImagePicker | empty list
| `setRequestCode` | Request code for starting ImagePicker | `100`
| `start` | Open ImagePicker |

What's new
--------
- Fixed bugs.
- Supported Android 10 (API 29)
- Updated to new UI/UX (remove selection overlay, add number indicator)
- Converted Java code to Kotlin code
- Upgraded Glide to v4.11, AndroidX to v1.1.0
- Replaced `savePath` option with `directoryName` option.
- Removed `keepScreenOn` config option

License
--------

Copyright (c) 2020 Nguyen Hoang Lam

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
