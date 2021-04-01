# :loudspeaker: SpeakUP - 통역 학습자를 위한 학습 보조 어플리케이션
<img alt="Logo" src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" width="200">

## :wave: Introduction
이 앱은 통역 학습자를 위한 학습 보조 어플리케이션입니다.
1. 수강 중인 과목과 과제 목록을 확인할 수 있습니다.
2. 녹음 기능을 통해 과제를 수행할 수 있습니다.
3. 통역 전사자료와 추임새 및 침묵 통계를 확인할 수 있습니다.

## :hammer: Development Environment
- Android Studio @4.0.1

## :bookmark: Application Version
- minSdkVersion : 21
- targetSdkVersion : 29

## :art: Program Structure
|         Activity         |                          Description                           |
| :----------------------: | :------------------------------------------------------------: |
|     `LoginActivity`      |                          로그인 화면                           |
|     `SignUpActivity`     |                         회원가입 화면                          |
|      `MainActivity`      |           사용자가 수강하는 과목 목록을 보여주는 화면           |
|     `CourseActivity`     |       선택한 과목의 상세 정보와 과제 목록을 보여주는 화면       |
|   `AssignmentActivity`   |             선택한 과제의 상세 정보를 보여주는 화면             |
|     `RecordActivity`     |              녹음 기능을 통해 과제를 수행하는 화면              |
|     `ResultActivity`     |       수행한 과제의 통역 전사자료 및 통계를 보여주는 화면        |

## :books: Libraries Used
```
implementation 'androidx.appcompat:appcompat:1.2.0'
implementation 'androidx.constraintlayout:constraintlayout:2.0.1'
implementation 'androidx.cardview:cardview:1.0.0'
implementation 'androidx.recyclerview:recyclerview:1.1.0'
testImplementation 'junit:junit:4.12'
androidTestImplementation 'androidx.test.ext:junit:1.1.2'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
implementation 'com.squareup.retrofit2:retrofit:2.6.0'
implementation("com.squareup.okhttp3:okhttp:4.0.1")
implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
implementation 'commons-io:commons-io:2.6'
implementation 'com.github.PhilJay:MPAndroidChart:v2.2.4'
```
