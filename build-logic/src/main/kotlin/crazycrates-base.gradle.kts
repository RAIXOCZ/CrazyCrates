plugins {
    id("crazycrates-language")
}

rootProject.group = "com.badbones69.crazycrates"
rootProject.version = "1.11.8"
rootProject.description = "Add unlimited crates to your server with 10 different crate types to choose from!"

repositories {
    /**
     * Everything else we need.
     */
    maven("https://jitpack.io")

    mavenCentral()
}