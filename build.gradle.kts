import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

group = "org.jetbrains.reflektTask"
/*
* To change version you should change the version in the following places:
*  - here (the main build.gradle.kts file)
*  - VERSION const in the Util.kt in the reflektTask-core module
*
* */
version = "1.5.31"

plugins {
    id("tanvd.kosogor") version "1.0.12" apply true
    kotlin("jvm") version "1.5.31" apply true
    id("com.github.gmazzo.buildconfig") version "3.0.3" apply false
    kotlin("kapt") version "1.5.31" apply true
}

allprojects {
    apply {
        plugin("kotlin")
    }

    tasks.withType<KotlinJvmCompile> {
        kotlinOptions {
            jvmTarget = "11"
            languageVersion = "1.5"
            apiVersion = "1.5"
            freeCompilerArgs = listOf("-Xjvm-default=compatibility")
        }
    }

    repositories {
        mavenCentral()
    }
}
