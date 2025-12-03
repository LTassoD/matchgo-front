
plugins {
    // Es importante usar 'alias()' para referenciar los plugins del archivo libs.versions.toml
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.compose) apply false
}
