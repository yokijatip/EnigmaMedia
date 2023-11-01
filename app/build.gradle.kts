plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.enigma.enigmamedia"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.enigma.enigmamedia"
        minSdk = 28
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
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
}

dependencies {

//    Google Maps
    implementation("com.google.android.gms:play-services-maps:17.0.0")


//    Compress Image
//    implementation("id.zelory:compressor:3.0.0")
    implementation("id.zelory:compressor:3.0.1")

//    Paging 3
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.12")

//    Mockito buat Testing
    val mockito_vesion = "3.12.4"
    testImplementation("org.mockito:mockito-core:$mockito_vesion")
    testImplementation("org.mockito:mockito-inline:$mockito_vesion")

//    Special Testing
    testImplementation("androidx.arch.core:core-testing:2.1.0")

//    Camera X
    val cameraxVersion = "1.2.3"
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")

    val room_version = "2.5.2"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

//    Coroutine
    val coroutineVersion = "1.3.9"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")

//    Retrofit & GSON Converter
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

//    OKHTTP untuk Debugging
    implementation("com.squareup.okhttp3:logging-interceptor:4.2.0")

//    Lifecycle ViewModel
    val viewModelVersion = "2.6.2"
    val activityKtxVersion = "1.7.2"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelVersion")
    implementation("androidx.activity:activity-ktx:$activityKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$viewModelVersion")

//    Data Store
    val dataStoreVersion = "1.0.0"
    implementation("androidx.datastore:datastore-preferences:$dataStoreVersion")

//    Glide
    val glideVersion = "4.16.0"
    implementation("com.github.bumptech.glide:glide:$glideVersion")



    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}