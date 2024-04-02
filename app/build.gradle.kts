plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp").version("1.8.0-1.0.8")
    id("kotlin-kapt")
}

android {
    namespace = "com.develex.baseapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.develex.baseapp"
        minSdk = 28
        targetSdk = 34
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val roomVersion = "2.6.1"
    val material3Version = "1.2.1"
    val composeSettingsVersion = "1.0.3"
    val calendarVersion = "2.5.0"
    val lifecycleVersion = "2.7.0"
    val datastoreVersion = "1.0.0"

//    Kotlin
    implementation("androidx.core:core-ktx:1.12.0")
//    Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
//    Datastore
    implementation("androidx.datastore:datastore-preferences:$datastoreVersion")
//    Material 3
    implementation("androidx.compose.material3:material3:$material3Version")
//    Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.ui:ui")
    implementation(platform("androidx.compose:compose-bom:2024.03.00"))
//    Compose-Settings
    implementation("com.github.alorma:compose-settings-storage-preferences:$composeSettingsVersion")
    implementation("com.github.alorma:compose-settings-ui-m3:$composeSettingsVersion")
//    Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
//    Calendar
    implementation("com.kizitonwose.calendar:compose:$calendarVersion")
//    Test & Debug
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}