plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
//    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "uz.gita.latizx.presenter"
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
//    kapt {
//        correctErrorTypes = true
//    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // navigation lib
    implementation(libs.voyager.hilt)

    //Coroutine
    implementation(libs.kotlinx.coroutines.android)

    // hilt
    implementation(libs.hilt.android)
//    kapt(libs.hilt.compiler)
    ksp("com.google.dagger:hilt-compiler:2.51.1")

    //paging
    implementation(libs.androidx.paging.compose)
    implementation (libs.androidx.paging.runtime)

    implementation(project(":usecase"))
    implementation(project(":common"))
}