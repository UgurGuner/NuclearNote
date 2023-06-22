plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.nuclearnote"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.nuclearnote"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
    }
    lint {
        checkReleaseBuilds = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(dependencyNotation = "androidx.activity:activity-compose:1.7.2")
    implementation(dependencyNotation = "androidx.compose.material:material:1.4.3")
    implementation(dependencyNotation = "androidx.compose.animation:animation:1.4.3")
    implementation(dependencyNotation = "androidx.compose.ui:ui-tooling:1.4.3")
    implementation(dependencyNotation = "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation(dependencyNotation = "androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation(dependencyNotation = "com.google.android.material:compose-theme-adapter:1.2.1")
    implementation(dependencyNotation = "com.google.accompanist:accompanist-appcompat-theme:0.16.0")
    implementation(dependencyNotation = "androidx.navigation:navigation-compose:2.6.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation(dependencyNotation = "androidx.core:core-splashscreen:1.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.room:room-ktx:2.5.2")
    implementation("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")
}

kapt {
    correctErrorTypes = true
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}