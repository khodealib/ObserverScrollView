// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Dependency.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependency.kotlin}")
        classpath("com.github.dcendents:android-maven-gradle-plugin:${Dependency.androidMavenGradlePlugin}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}