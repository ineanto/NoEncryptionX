import gradle.kotlin.dsl.accessors._f2a9aebd8c5798d32ebc7e5891a02610.annotationProcessor
import gradle.kotlin.dsl.accessors._f2a9aebd8c5798d32ebc7e5891a02610.compileOnly

plugins {
    `java-library`
    `maven-publish`

    id("io.papermc.paperweight.userdev")
}

group = "xyz.ineanto"
version = "6.0"

// Set Java 17 for all projects
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
    mavenLocal()
    mavenCentral()

    maven("https://repo.papermc.io/repository/maven-public")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

dependencies {
    compileOnly(project(":common"))

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
