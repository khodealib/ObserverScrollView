plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(Config.compileSdk)

    defaultConfig {
        applicationId(Config.applicationId)
        minSdkVersion(Config.minSdkSample)
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
    implementation("androidx.core:core-ktx:${Dependency.core}")
    implementation("androidx.appcompat:appcompat:${Dependency.appcompat}")
    implementation("com.google.android.material:material:${Dependency.material}")
    testImplementation("junit:junit:${Dependency.junit}")
    androidTestImplementation("androidx.test.ext:junit:${Dependency.junitExt}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Dependency.espresso}")
}