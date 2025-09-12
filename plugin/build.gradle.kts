plugins {
    id("buildlogic.paper-api")
    id("com.gradleup.shadow") version "9.1.0"
    id("xyz.jpenilla.run-paper") version "3.0.0"
}

group = "xyz.ineanto.noencryptionx"
version = "6.0"

description = "NoEncryptionX - Plugin"

val versions = listOf("1.19", "1.19.1", "1.19.2", "1.19.3", "1.19.4", "1.20.1")

dependencies {
    versions.forEach {
        implementation(project(":$it"))
    }

    // TODO (Ineanto, 08/09/2025): Add adventure to libs.versions.toml
    implementation("net.kyori:adventure-api:4.24.0")
    implementation(project(":common"))
}

tasks {
    runServer {
        minecraftVersion("1.20.1")
    }

    shadowJar {
        archiveFileName.set("NoEncryptionX-${project.version}.jar")
        relocate("net.kyori", "xyz.ineanto.noencryptionx.libs.adventure")
        //minimize()
    }

    processResources {
        from("src/main/resources")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        filesMatching("*.yml") {
            expand("version" to "6.0")
        }
    }
}