
# Tnkfactory SDK Rwd

## list 

1. [SDK Integration](#1-sdk-integration)

 * [Add library](#add-library)
 * [manifest configuration](#manifest-configuration)
   * [Add tnk Application ID](#add-tnk-application-id)
   * [manifest activity and scheme](#manifest-activity-and-scheme)
   * [manifest example](#manifest-example)
 * [use proguard](#use-proguard)
 * [coppa setting](#coppa-setting)
 
2. [Display offerwall](#2-display-offerwall)

## 1. SDK Integration

### Add library

add following maven repository to your project's build.gradle or settings.gradle file.
```gradle
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        
        // add this line
        maven { url "https://repository.tnkad.net:8443/repository/public/" }
    }
}
rootProject.name = "project_name"
include ':app'
```

add dependency to your app/build.gradle file.
```gradle
dependencies {
    implementation 'com.tnkfactory:rwd_payplus:1.0.0'
}
```

### manifest configuration

#### manifest permission
add permission to your AndroidManifest.xml file.
```xml
<!-- internet -->
<uses-permission android:name="android.permission.INTERNET" />
<!-- wifi access for video ad -->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<!-- get advertising id -->
<uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
```

#### Add tnk Application ID
first tnkfactory's incentive manager page, register your app. and get your application id.
add it to your AndroidManifest.xml file.
(*your-application-id-from-tnk-site* replace to your application id.)

```xml
<application>

    <meta-data android:name="tnkad_app_id" android:value="your-application-id-from-tnk-site" />

</application>
```

#### manifest activity and scheme
add following activity to your AndroidManifest.xml file.
scheme is 'tnkrwd' + 'your application number'
(ask to tnkfactory's manager for your application number.)

```xml
<activity android:name="com.tnkfactory.ad.AdWallActivity"
    android:configChanges="orientation|screenSize"
    android:theme="@style/Theme.AppCompat.Light.NoActionBar"
    android:hardwareAccelerated="true"
    android:screenOrientation="portrait"
    android:windowSoftInputMode="adjustResize">
    <intent-filter android:autoVerify="true">
        <action android:name="android.intent.action.VIEW" />

        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <!-- tnkrwd100014://payplus -->
        <data android:host="payplus" android:scheme="tnkrwd100014" />
    </intent-filter>
</activity>
```

AndroidManifest.xml example
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.tnkfactory.tnkofferer">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="com.google.android.gms.permission.AD_ID"/>


    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">

        ...
        ...
        <activity
            android:name="com.tnkfactory.ad.rwdplus.kakao.scene.RwdPlusLoginActivity"
            android:exported="true"
            android:launchMode="singleTask"
            android:screenOrientation="portrait"
            android:windowSoftInputMode="adjustResize">

            <intent-filter android:autoVerify="true">
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <!-- tnkrwd100014://payplus -->
                <data android:host="payplus" android:scheme="tnkrwd100014" />
            </intent-filter>
        </activity>
        <activity
            android:name="com.tnkfactory.ad.AdWallActivity"
            android:exported="true"
            android:screenOrientation="portrait"
            android:theme="@style/Theme.AppCompat.DayNight.NoActionBar"
            android:windowSoftInputMode="adjustResize" />
        <!-- App ID -->
        <meta-data
            android:name="tnkad_app_id"
            android:value="30408070-4051-9322-2239-15040708030f" />
        ...
        ...
    </application>
</manifest>
```

### use proguard

if you use proguard, add following code to your proguard configuration file(proguard-rules.pro).
```
-keep class com.tnkfactory.** { *;}
```

### coppa setting
coppa is [Children's Online Privacy Protection Act](https://www.ftc.gov/tips-advice/business-center/privacy-and-security/children's-privacy) and related regulations. Google requires that apps that target children under the age of 13 comply with the relevant laws. Please set the following options so that age-appropriate ads can be displayed.

```kotlin
TnkRwdPlus(context).setCOPPA(true)  // on : only child-directed
TnkRwdPlus(context).setCOPPA(false) // off
```

## 2. Display offerwall

```diff
- warning : In test mode, you must register the test device as a development device to display the ad list normally.
```

link : [Register test device](https://github.com/tnkfactory/android-sdk-rwd/blob/master/reg_test_device.md)

you follow the steps below to display the ad list.

1) initialize TnkRwdPlus object.
2) coppa setting
3) show offerwall

```kotlin
public class MainActivity extends AppCompatActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize TnkRwdPlus object.
        val tnkRwdPlus = TnkRwdPlus(this).apply {
            // coppa setting
            setCOPPA(false)
            // scheme name must be same with RwdPlusLoginActivity's scheme in AndroidManifest.xml
            // example <data android:host="payplus" android:scheme="tnkrwd100014" />
            setScheme("tnkrwd100014")
        }

        // button event
        findViewById<View>(R.id.tv_test).setOnClickListener {
            // show offerwall
            tnkRwdPlus.showOfferwall(this@MainActivity)
        }
    }
}
```



