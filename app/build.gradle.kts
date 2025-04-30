plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")//id("kotlin-kapt")
}

android {
    namespace = "uz.gita.latizx.tbcmobile"
    compileSdk = 35

    defaultConfig {
        applicationId = "uz.gita.latizx.tbcmobile"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        compose = true
    }
//    kapt {
//        correctErrorTypes = true
//    }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // navigation lib
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.hilt)
    implementation(libs.voyager.bottom.sheet.navigator)
    implementation(libs.voyager.tab.navigator)
    implementation(libs.voyager.transitions)

    // hilt
    implementation(libs.hilt.android)
//    kapt(libs.hilt.compiler)
    ksp("com.google.dagger:hilt-compiler:2.51.1")

    //paging
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime)

    //LottieAnimation
    implementation (libs.lottie.compose)

    //pdf
    implementation("com.itextpdf:itextg:5.5.10")


    // Map
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.maps.android:android-maps-utils:3.7.0")

    // Google Maps Compose library
    val mapsComposeVersion = "4.4.1"
    implementation("com.google.maps.android:maps-compose:$mapsComposeVersion")
    // Google Maps Compose utility library
    implementation("com.google.maps.android:maps-compose-utils:$mapsComposeVersion")
    // Google Maps Compose widgets library
    implementation("com.google.maps.android:maps-compose-widgets:$mapsComposeVersion")

    implementation("com.google.accompanist:accompanist-permissions:0.37.0")
    
    //coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.androidx.biometric)

    implementation(project(":common"))
    implementation(project(":presenter"))
    implementation(project(":entity"))

}