plugins {
//    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.android.application")
    id("com.google.gms.google-services") version "4.4.2"

}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("C:\\Users\\R4FFI\\Documents\\android project\\crudapp.jks")
            storePassword = "123456"
            keyAlias = "crudkey"
            keyPassword = "123456"
        }
        create("release") {
            storeFile = file("C:\\Users\\R4FFI\\Documents\\android project\\crudapp.jks")
            storePassword = "123456"
            keyAlias = "crudkey"
            keyPassword = "123456"
        }
    }
    namespace = "com.example.crud"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.crud"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
    buildTypes {
        release {
            isMinifyEnabled = false
            applicationIdSuffix = ".release"
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth")
}