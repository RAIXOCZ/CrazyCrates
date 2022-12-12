plugins {
    id("crazycrates-base")

    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    compileOnly("me.carleslc.Simple-Yaml", "Simple-Yaml", "1.8.2")

    val adventureVersion = "4.12.0"

    compileOnly("net.kyori", "adventure-api", adventureVersion)
    compileOnly("net.kyori", "adventure-text-minimessage", adventureVersion)
}

tasks {
    shadowJar {
        archiveFileName.set("${rootProject.name}-${rootProject.version}-api.jar")
    }
}