plugins {
    id("buildlogic.paperweight")
}

dependencies {
    paperweight.paperDevBundle("1.20.3-R0.1-SNAPSHOT")
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.REOBF_PRODUCTION

description = "NoEncryptionX for 1.20.3+"
