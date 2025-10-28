plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "ru.mirea.golysheva.lesson10"
    compileSdk = 36

    defaultConfig {
        applicationId = "ru.mirea.golysheva.lesson10"
        minSdk = 33
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // подключаем наши модули
    implementation(project(":domain"))
    implementation(project(":data"))

    // UI и базовые зависимости
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.8.6")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.8.6")
}
