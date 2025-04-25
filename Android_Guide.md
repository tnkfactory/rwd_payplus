
# Tnkfactory SDK Rwd

## 목차

1. [SDK 설정하기](#1-sdk-설정하기)

* [라이브러리 등록](#라이브러리-등록)
* [Manifest 설정하기](#manifest-설정하기)
    * [Application ID 설정하기](#application-id-설정하기)
    * [권한 설정](#권한-설정)
    * [Activity tag 추가하기](#activity-tag-추가하기)
* [Proguard 사용](#proguard-사용)
* [COPPA 설정](#coppa-설정)

2. [광고 목록 띄우기](#2-광고-목록-띄우기)


## 1. SDK 설정

### 라이브러리 등록

project-root/build.gradle 또는 project-root/settings.gradle 파일에 아래와 같이 maven repository를 추가해주세요.
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
        
        // 경로를 추가해 주시기 바랍니다.
        maven { url "https://repository.tnkad.net:8443/repository/public/" }
    }
}
rootProject.name = "project_name"
include ':app'
```

app/build.gradle 파일에 dependency를 추가해주세요.
```gradle
dependencies {
    implementation 'com.tnkfactory:rwd_payplus:1.1.19'
}
```

### Manifest 설정하기

#### 권한 설정
아래와 같이 권한 사용을 추가합니다.
```xml
<!-- 인터넷 -->
<uses-permission android:name="android.permission.INTERNET" />
<!-- 동영상 광고 재생을 위한 wifi접근 -->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<!-- 광고 아이디 획득 -->
<uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
```

#### tnk Application ID 설정하기
먼저 Tnkfactory의 Incentive 관리자 페이지에서 광고 매체(안드로이드 앱)를 등록합니다.
등록한 매체의 app id를 확인합니다. AndroidMenifest.xml 파일의 application tag 안에 아래와 같이 설정합니다.
(*your-application-id-from-tnk-site* 부분을 실제 App ID 값으로 변경하세요.)

```xml
<application>

    <meta-data android:name="tnkad_app_id" android:value="your-application-id-from-tnk-site" />

</application>
```

### manifest configuration

#### 권한 설정
add permission to your AndroidManifest.xml file.
```xml
<!-- internet -->
<uses-permission android:name="android.permission.INTERNET" />
<!-- wifi access for video ad -->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<!-- get advertising id -->
<uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
```

#### tnk Application ID 설정하기
first tnkfactory's incentive manager page, register your app. and get your application id.
add it to your AndroidManifest.xml file.

```xml
<application>

    <meta-data android:name="tnkad_app_id" android:value="your-application-id-from-tnk-site" />

</application>
```

#### Activity 추가

광고 목록을 띄우기 위한 Activity를 <activity/>로 아래와 같이 설정합니다.

이때 scheme의 값을 tnkrwd + 매체번호의 조합으로 설정 하시기 바랍니다.
(해당 설정에 대해서는 영업팀에 문의 바랍니다.)
```xml
<!-- 광고 목록 activity -->
<activity android:name="com.tnkfactory.ad.AdWallActivity"/>
<!-- 카카오 로그인 activity -->
<activity android:name="com.tnkfactory.ad.rwdplus.kakao.scene.RwdPlusLoginActivity" >
    
    <intent-filter android:autoVerify="true">
        <action android:name="android.intent.action.VIEW" />

        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <!-- tnkrwd100014://payplus -->
        <data android:host="payplus" android:scheme="tnkrwd100014" />
    </intent-filter>
</activity>

```

AndroidManifest.xml의 내용 예시
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.tnkfactory.tnkofferer">

    // 인터넷
    <uses-permission android:name="android.permission.INTERNET" />
    // 동영상 광고 재생을 위한 wifi접근
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    // 광고 아이디 획득
    <uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
    // 기타 앱에서 사용하는 권한
    //...
    //...
    
    
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


### Proguard 사용

Proguard를 사용하실 경우 Proguard 설정내에 아래 내용을 반드시 넣어주세요.

```
-keep class com.tnkfactory.** { *;}
```

### COPPA 설정

COPPA는 [미국 어린이 온라인 개인정보 보호법](https://www.ftc.gov/tips-advice/business-center/privacy-and-security/children's-privacy) 및 관련 법규입니다. 구글 에서는 앱이 13세 미만의 아동을 대상으로 서비스한다면 관련 법률을 준수하도록 하고 있습니다. 연령에 맞는 광고가 보일 수 있도록 아래의 옵션을 설정하시기 바랍니다.

```kotlin
TnkRwdPlus(context).setCOPPA(true)  // ON - 13세 미만 아동을 대상으로 한 서비스 일경우 사용
TnkRwdPlus(context).setCOPPA(false) // OFF
```

## 2. 광고 목록 띄우기


```diff
- 주의 : 테스트 상태에서는 테스트하는 장비를 개발 장비로 등록하셔야 광고목록이 정상적으로 나타납니다.
```
링크 : [테스트 단말기 등록](https://github.com/tnkfactory/android-sdk-rwd/blob/master/reg_test_device.md)

다음과 같은 과정을 통해 광고 목록을 출력 하실 수 있습니다.

1) TNK SDK 초기화

2) COPPA 설정

3) 광고 목록 출력

광고 목록을 출력하는 Activity의 예제 소스

kotlin
```kotlin
public class MainActivity extends AppCompatActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TNK 오퍼월 초기화
        val tnkRwdPlus = TnkRwdPlus(this).apply {
            // coppa 설정 (미국 어린이 온라인 개인정보 보호법)https://www.ftc.gov/business-guidance/privacy-security/childrens-privacy
            setCOPPA(false)
            // scheme 설정 androidManifest.xml의 RwdPlusLoginActivity의 scheme과 동일해야 합니다.
            // 예시 <data android:host="payplus" android:scheme="tnkrwd100014" />
            setScheme("tnkrwd100014")
        }

        // 오퍼월 화면 출력하는 버튼 설정
        findViewById<View>(R.id.tv_test).setOnClickListener {
            // 오퍼월 화면을 출력합니다.
            tnkRwdPlus.showOfferwall(this@MainActivity)
        }
    }
}
```
