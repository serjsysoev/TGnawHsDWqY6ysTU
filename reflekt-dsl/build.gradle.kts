import tanvd.kosogor.proxy.publishJar

group = rootProject.group
version = rootProject.version

dependencies {
    api(kotlin("reflect"))
}

publishJar {
    publication {
        artifactId = "io.reflekt.dsl"
    }
}