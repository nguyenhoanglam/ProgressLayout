## ProgressLayout
An extension of RelativeLayout that helps show loading, empty and error layout.

## Screenshot
<img src="https://cloud.githubusercontent.com/assets/4979755/18380044/bab54c72-769f-11e6-8427-cdf6d3920b1c.png" height="683" width="384">
<img src="https://cloud.githubusercontent.com/assets/4979755/18380045/bb1fb300-769f-11e6-89b1-5fd3742385f1.png" height="683" width="384">
<img src="https://cloud.githubusercontent.com/assets/4979755/18380046/bb5f49a2-769f-11e6-8112-0f46b8c0d526.png" height="683" width="384">
## Download
Add to your module's build.gradle:
```java
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
and:
```java
dependencies {
    compile 'com.github.nguyenhoanglam:ProgressLayout:1.0.0'
}
```
## How to use 
- Use ProgressLayout like RelativeLayout in xml file.
```java
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Progress Layout");

        ProgressLayout progressLayout = (ProgressLayout) findViewById(R.id.progressLayout);

        // Show progress layout and keep main views visible
        // skipIds is a list of view's ids which you want to show with ProgressLayout (in this case is the Toobar)
        List<Integer> skipIds = new ArrayList<>();
        skipIds.add(R.id.toolbar);

        progressLayout.showLoading(skipIds);
        progressLayout.showEmpty(ContextCompat.getDrawable(this, R.drawable.ic_empty), "Empty data",skipIds);
        progressLayout.showError(ContextCompat.getDrawable(this, R.drawable.ic_no_connection), "No connection", "RETRY", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Reloading...", Toast.LENGTH_SHORT).show();
            }
        },skipIds);


        // Show progress layout, hide all main views
        progressLayout.showLoading();
        progressLayout.showEmpty(ContextCompat.getDrawable(this, R.drawable.ic_empty), "Empty data");
        progressLayout.showError(ContextCompat.getDrawable(this, R.drawable.ic_no_connection), "No connection", "RETRY", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Reloading...", Toast.LENGTH_SHORT).show();
            }
        });

    }
```
- Custom ProgressLayout's attributes
```java
<com.nguyenhoanglam.progresslayout.ProgressLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/progressLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:emptyContentTextColor="@color/grey"
    app:emptyContentTextSize="14sp"
    app:emptyImageHeight="200dp"
    app:emptyImageWidth="2000dp"
    app:errorButtonTextColor="@color/teal"
    app:errorButtonTextSize="14sp"
    app:errorContentTextColor="@color/grey"
    app:errorContentTextSize="14sp"
    app:errorImageHeight="200dp"
    app:errorImageWidth="200dp"
    app:loadingProgressBarColor="@color/teal"
    app:loadingProgressBarRadius="100dp"
    app:loadingProgressBarSpinWidth="8dp"/>
```
##License
Copyright 2016 Nguyen Hoang Lam

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
