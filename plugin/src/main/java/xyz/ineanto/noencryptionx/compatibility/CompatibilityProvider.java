package xyz.ineanto.noencryptionx.compatibility;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class CompatibilityProvider {
    @Getter
    private PacketChannelHandler packetChannelHandler;
    @Getter
    private PlayerChannel playerChannel;

    private final Logger logger = Logger.getLogger("NoEncryptionX/Compat");

    private final Map<Set<String>, String> versionsToPackage = Map.of(
            Set.of("1.19", "1.19.1", "1.19.2"), "v1_19_R1",
            Set.of("1.19.3"), "v1_19_R2",
            Set.of("1.19.4"), "v1_19_R3",
            Set.of("1.20", "1.20.1"), "v1_20_R1",
            Set.of("1.20.2"), "v1_20_R2",
            Set.of("1.20.3", "1.20.4"), "v1_20_R3",
            Set.of("1.20.5", "1.20.6"), "v1_20_R4"
    );

    public void setup() {
        try {
            final Class<?> packetChannelHandlerClass = getVersionnedClass(PacketChannelHandler.class);
            final Class<?> playerChannelClass = getVersionnedClass(PlayerChannel.class);

            packetChannelHandler = (PacketChannelHandler) packetChannelHandlerClass.getConstructors()[0].newInstance();
            playerChannel = (PlayerChannel) playerChannelClass.getConstructors()[0].newInstance();

            logger.info("Loaded compatibility for " + Bukkit.getBukkitVersion());
        } catch (InvocationTargetException | ClassNotFoundException | InstantiationException | IllegalAccessException |
                 ClassCastException exception) {
            packetChannelHandler = null;
            playerChannel = null;
            exception.printStackTrace();
        }
    }

    public String getVersionStripped() {
        return Bukkit.getBukkitVersion().split("-")[0];
    }

    public Class<?> getVersionnedClass(Class<?> clazz) throws ClassNotFoundException {
        final String versionStringWithoutDots = getVersionStripped().replace(".", "_");
        return Class.forName(clazz.getName() + "_" + versionStringWithoutDots);
    }
}