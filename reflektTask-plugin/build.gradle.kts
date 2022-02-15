import tanvd.kosogor.proxy.publishJar

group = rootProject.group
version = rootProject.version

plugins {
    kotlin("kapt")
}

dependencies {
    implementation(kotlin("compiler-embeddable"))

    implementation("com.google.auto.service", "auto-service-annotations", "1.0")
    kapt("com.google.auto.service", "auto-service", "1.0")

    implementation(project(":reflektTask-core"))
}

publishJar {}
