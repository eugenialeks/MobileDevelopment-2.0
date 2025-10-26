plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "ru.mirea.golysheva.data"
    compileSdk = 36

    defaultConfig {
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
    implementation(project(":domain"))

    // Аннотации (как было)
    implementation("androidx.annotation:annotation:1.8.1")
    implementation(libs.firebase.auth)
    implementation("com.google.android.material:material:1.12.0")
    // Room — экспортируем наружу
    val roomVersion = "2.6.1"
    api("androidx.room:room-runtime:$roomVersion")          // ← было implementation
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    testImplementation(libs.junit)
}
