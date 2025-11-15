plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.gestionresiduos"   // <- tu package/namespace
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.gestionresiduos"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        vectorDrawables { useSupportLibrary = true }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }

    }

    buildTypes {
        debug {
            // ingresar aqui url (termina en /) cuando tengas Railway o algun host listo
            buildConfigField("String","BASE_URL","\"https://<ingresar-aqui-url>/\"")
        }
        release {
            isMinifyEnabled = true
            buildConfigField("String","BASE_URL","\"https://<ingresar-aqui-url>/\"")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = "latest" }
    kotlinOptions { jvmTarget = "17" }
    packaging { resources.excludes += "/META-INF/{AL2.0,LGPL2.1}" }
}

dependencies {
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:latest.release"))

    // UI base
    implementation("androidx.activity:activity-compose")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Navigation
    implementation("androidx.navigation:navigation-compose")

    // Animaciones Compose + Nav animado (como UINavegacion/AnimacionesEstado)
    implementation("androidx.compose.animation:animation")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.36.0")

    // ViewModel/Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose")
    implementation("androidx.lifecycle:lifecycle-runtime-compose")

    // Adaptabilidad (opcional)
    implementation("androidx.window:window:1.3.0")

    // Red (Retrofit/Moshi/OkHttp) para consumir Railway
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Corrutinas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}
