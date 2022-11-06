
plugins {
    id("clusterization.android.library")
    id("kotlin-kapt")
    alias(libs.plugins.ksp)
}

android {
    defaultConfig {
        // The schemas directory contains a schema file for each version of the Room database.
        // This is required to enable Room auto migrations.
        // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    namespace = "com.hotspots.android.apps.clusterization.core.database"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
}