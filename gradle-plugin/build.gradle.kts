import tanvd.kosogor.proxy.publishJar
import tanvd.kosogor.proxy.publishPlugin

group = rootProject.group
version = rootProject.version

plugins {
    kotlin("kapt")
}

dependencies {
    implementation(kotlin("gradle-plugin-api"))
    implementation(project(":reflektTask-core"))
    implementation(kotlin("compiler-embeddable"))
}

publishPlugin {
    id = "org.jetbrains.reflektTask"
    displayName = "ReflektTask"
    implementationClass = "org.jetbrains.reflektTask.plugin.ReflektTaskSubPlugin"
    version = project.version.toString()

    info {
        description = "Reflekt task"
    }
}

publishJar {}
