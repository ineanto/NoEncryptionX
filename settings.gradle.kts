pluginManagement {
    includeBuild("build-logic")
}

rootProject.name = "NoEncryptionX"

val modules = listOf(
    "1.19", "1.19.1", "1.19.2", "1.19.3", "1.19.4",
    "1.20.1", "1.20.2",
    "common"
)

modules.forEach {
    include(it)
}

include("plugin")
include("common")