
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.google.devtools.ksp)

}
android {
    namespace = "com.example.gestionresiduos"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gestionresiduos"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables { useSupportLibrary = true }
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://tu-url-de-desarrollo.com/api/\"")
        }
        release {
            isMinifyEnabled = true
            buildConfigField("String", "BASE_URL", "\"https://tu-url-de-produccion.com/api/\"")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    kotlin {
        jvmToolchain(17)
    }


    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        debug {
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://microserviciosappecologica-production.up.railway.app/\""
            )
        }
        release {
            isMinifyEnabled = false
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://microserviciosappecologica-production.up.railway.app/\""
            )
        }
    }



    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }

}

dependencies {
    // LA SPLASH SCREEN API
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Core y UI de Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.com.google.android.material.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    coreLibraryDesugaring(libs.android.desugar.jdk.libs)

    // Jetpack Compose - BOM (gestiona las versiones)
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // JETPACK PAGING
    implementation("androidx.paging:paging-runtime-ktx:3.3.0")

    // Dependencias de Compose (sin versiones)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Iconos extendidos
    implementation("androidx.compose.material:material-icons-extended")

    // Navegación
    implementation(libs.androidx.navigation.compose)
    implementation("com.google.accompanist:accompanist-navigation-animation:0.32.0")

    // DEPENDENCIAS DE RETROFIT
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")


    // Accompanist
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.permissions)



    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Corrutinas
    implementation(libs.kotlinx.coroutines.android)

    // JUnit 5 (el framework de pruebas estándar para Java/Kotlin)
    testImplementation(libs.junit) // Asumiendo que tienes "junit = { group = "junit", name = "junit", version = "4.13.2" }" en tu libs.versions.toml
    testImplementation(libs.androidx.junit) // Para extensiones de Android
    testImplementation(libs.androidx.espresso.core) // Framework de pruebas de UI (aunque no lo usemos directamente, es bueno tenerlo)

    // Mockito (para simular dependencias, como el Repository)
    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")

    // Corrutinas (para probar código asíncrono en ViewModels)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")

    // LiveData & ViewModel Testing
    testImplementation("androidx.arch.core:core-testing:2.2.0")


    // Coil (Imágenes)
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Dependencies for Privacy Sandbox
    implementation("androidx.privacysandbox.tools:tools:1.0.0-alpha13")
    implementation("androidx.privacysandbox.sdkruntime:sdkruntime-client:1.0.0-alpha12")
    implementation("androidx.privacysandbox.sdkruntime:sdkruntime-core:1.0.0-alpha12")
    implementation("androidx.privacysandbox.sdkruntime:sdkruntime-provider:1.0.0-alpha12")

    // --- DEPENDENCIAS DE ROOM ---
//    val room_version = "2.6.1" // Usa la última versión estable
//    implementation("androidx.room:room-runtime:$room_version")
//    ksp("androidx.room:room-compiler:$room_version") // <-- Usa ksp en lugar de kapt
//    // Soporte opcional para Coroutines en Room (muy recomendado)
//    implementation("androidx.room:room-ktx:$room_version")
//    implementation("com.google.code.gson:gson:2.10.1")
    // --- FIN DE DEPENDENCIAS DE ROOM ---

}
