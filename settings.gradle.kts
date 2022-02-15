rootProject.name = "reflektTask"

include(":reflektTask-core")
include(":reflektTask-plugin")
include(":gradle-plugin")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
