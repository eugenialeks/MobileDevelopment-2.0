plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "ru.mirea.golysheva.data"
    compileSdk = 36

    defaultConfig {
        // можно оставить 33, но обычно делаем как в app
        minSdk = 33
        consumerProguardFiles("consumer-rules.pro")
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
    // ✅ data зависит от domain
    implementation(project(":domain"))

    // (опционально) полезная не-UI зависимость
    implementation("androidx.annotation:annotation:1.8.1")
}
