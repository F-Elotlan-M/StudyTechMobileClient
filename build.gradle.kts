// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.17")
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.7.1")
    }
}
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}