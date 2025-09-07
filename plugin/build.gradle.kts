plugins {
    id("buildlogic.paper-api")
}

group = "xyz.ineanto.noencryptionx"
version = "6.0"

description = "NoEncryptionX - Plugin"

val versions = listOf("1.19", "1.19.1", "1.19.2", "1.19.3", "1.19.4", "1.20.1")

dependencies {
    versions.forEach {
        implementation(project(":$it"))
    }
}

tasks {
    processResources {
        from("src/main/resources")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        filesMatching("*.yml") {
            expand("version" to version)
        }
    }

}