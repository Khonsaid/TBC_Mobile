plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "uz.gita.latizx.entity"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String","BASE_URL","\"http://173.212.232.116/mobile-bank/v1/\"")
        }
        debug{
            buildConfigField("String","BASE_URL","\"http://173.212.232.116/mobile-bank/v1/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures{
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //Coroutine
    implementation(libs.kotlinx.coroutines.android)

    // Chuck interceptor
    debugImplementation(libs.library)
    releaseImplementation(libs.library.no.op)

    // REST API: Adding retrofit to the mainLayer
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)
    implementation(libs.com.squareup.retrofit2.converter.gson)
    implementation(libs.logging.interceptor)

    //Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    kapt(libs.androidx.room.compiler)

    //Gson
    implementation(libs.gson)

    //paging
    implementation(libs.androidx.paging.compose)
    implementation (libs.androidx.paging.runtime)

    implementation(project(":common"))
}