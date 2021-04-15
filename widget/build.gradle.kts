plugins {
    id("com.android.library")
    id("com.github.dcendents.android-maven")
    kotlin("android")
}

group="com.github.khodealib"

android {
    compileSdkVersion(Config.compileSdk)

    defaultConfig {
        minSdkVersion(Config.minSdkLib)
        targetSdkVersion(Config.targetSdk)
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Dependency.kotlin}")
    implementation("com.google.android.material:material:${Dependency.material}")
    testImplementation("junit:junit:${Dependency.junit}")
    androidTestImplementation("androidx.test.ext:junit:${Dependency.androidJUnit}")
}