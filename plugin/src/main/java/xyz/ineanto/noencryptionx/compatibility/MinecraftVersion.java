package xyz.ineanto.noencryptionx.compatibility;

import org.bukkit.Bukkit;

import java.util.Set;
import java.util.logging.Logger;

public enum MinecraftVersion {
    v1_19(Set.of("1.19", "1.19.1", "1.19.2", "1.19.3", "1.19.4")),
    v1_20(Set.of("1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4", "1.20.5", "1.20.6")),
    v1_21(Set.of("1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4", "1.21.5", "1.21.6", "1.21.7", "1.21.8", "1.21.9", "1.21.10", "1.21.11"));

    private final Set<String> versions;

    MinecraftVersion(Set<String> versions) {
        this.versions = versions;
    }

    public static String getVersionStripped() {
        return Bukkit.getBukkitVersion().split("-")[0];
    }

    public static MinecraftVersion current() {
        final String version = Bukkit.getBukkitVersion().split("-")[0];

        for (MinecraftVersion value : values()) {
            if (value.versions.stream().anyMatch(s -> s.equals(version))) {
                return value;
            }
        }

        final Logger logger = Logger.getLogger("NoEncryptionX");
        logger.info("Unable to determine Minecraft version, falling back to latest");
        return MinecraftVersion.v1_21;
    }
}
