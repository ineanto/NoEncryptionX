plugins {
    `java-library`
    `maven-publish`
}

group = "xyz.ineanto"
version = "1.1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

dependencies {
    compileOnly(libs.findLibrary("spigot-api-one-nineteen").get())
    compileOnly(libs.findLibrary("netty-transport").get())

    compileOnly(libs.findLibrary("lombok").get())
    annotationProcessor(libs.findLibrary("lombok").get())
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

// UTF-8 Encoding
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}
