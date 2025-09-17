plugins {
    id("buildlogic.paper-api")
    id("com.gradleup.shadow") version "9.1.0"
    id("xyz.jpenilla.run-paper") version "3.0.0"
}

group = "xyz.ineanto.noencryptionx"
version = "6.0"

description = "NoEncryptionX - Plugin"

dependencies {
    // Doing a for loop here causes Gradle to NOT import the dependencies correctly???
    // So we have to manually add them one by one.
    // fuck my life.
    implementation(project(":v1_19_R1", configuration = "reobf"))
    implementation(project(":v1_19_R2", configuration = "reobf"))
    implementation(project(":v1_19_R3", configuration = "reobf"))
    implementation(project(":v1_20_R1", configuration = "reobf"))
    implementation(project(":v1_20_R2", configuration = "reobf"))
    implementation(project(":v1_20_R3", configuration = "reobf"))
    implementation(project(":v1_20_R4", configuration = "reobf"))

    // Mojang-Mapped

    implementation(project(":v1_21"))

    implementation(libs.adventure)
    implementation(libs.json)
    implementation(project(":common"))
}

tasks {
    runServer {
        minecraftVersion("1.21.8")
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