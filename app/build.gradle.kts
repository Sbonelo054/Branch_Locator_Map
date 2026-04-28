plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.parcelize")
}

android {
    namespace = "com.sa.branchlocatormap"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.sa.branchlocatormap"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // Google Maps SDK for Android
    implementation(libs.places)
    implementation(libs.play.services.maps)
    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.room.runtime)

    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.common)
    implementation(libs.gson)
    implementation(libs.play.services.location)
    implementation(libs.koin.androidx.compose)

    // Kotest
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)

    // MockK
    testImplementation(libs.mockk)

    // Coroutines test
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.maps.compose)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}