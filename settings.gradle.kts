pluginManagement {
    includeBuild("build-logic")
}

rootProject.name = "NoEncryptionX"

val modules = listOf(
    "v1_19_R1", "v1_19_R2", "v1_19_R3",
    "v1_20_R1", "v1_20_R2", "v1_20_R3", "v1_20_R4",
    "v1_21_R1", "v1_21_R2", "v1_21_R3", "v1_21"
)

modules.forEach {
    include(it)
}

include("plugin")
include("common")