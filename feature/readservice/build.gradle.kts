plugins {
    id("clusterization.android.feature")
    id("kotlin-kapt")
}

android {
    namespace = "com.hotspots.android.apps.clusterization.feature.map"
}

dependencies {
    implementation(project(":core:database"))

    implementation(libs.android.gms.maps)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.room.runtime)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
}
