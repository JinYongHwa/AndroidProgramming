
## build.gradle(app)
``` grdle
apply plugin: 'com.android.application'

android {
    
    //컴파일 시킬 SDK 버전
    compileSdkVersion 28
    defaultConfig {
        applicationId "kr.ac.mjc.build_example"     //어플리케이션의 고유한 ID값(패키지명)
        minSdkVersion 15                            //어플리케이션의 최소 OS 버전
        targetSdkVersion 28                         //최적화시킬 SDK 버전
        versionCode 4                               //애플리케이션의 버전코드
        versionName "1.${android.defaultConfig.versionCode-1}"  //애플리케이션의 버전명
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
        setProperty("archivesBaseName","example-${versionName}") //apk 파일명
    }
    
    //서명파일 설정정보
    signingConfigs {
        release {
            storeFile file("build_test.jks")
            storePassword "mjc2018"
            keyAlias "mjc"
            keyPassword "mjc2018"
            v2SigningEnabled true
        }
    }
    buildTypes {
        release {
            signingConfig signingConfigs.release    //서명파일 설정정보
            minifyEnabled false                     
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
}
```

## activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/version_tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</android.support.constraint.ConstraintLayout>
```

## MainActivity.java
``` java
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView versionTv=findViewById(R.id.version_tv);
        try {
            PackageInfo info=getPackageManager().getPackageInfo(getPackageName(),0);
            int versionCode=info.versionCode;
            String versionName=info.versionName;
            String message=String.format("현재앱의 버전코드는 %d이고 버전명은 %s 입니다",versionCode,versionName);
            versionTv.setText(message);
            Log.d("test",message);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```
