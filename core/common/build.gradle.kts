plugins {
    id("clusterization.android.library")
    id("kotlin-kapt")
}

android {
    namespace = "com.hotspots.android.apps.clusterization.core.common"
}

dependencies {
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.kotlinx.coroutines.android)
}