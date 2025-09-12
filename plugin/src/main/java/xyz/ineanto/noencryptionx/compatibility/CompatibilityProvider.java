package xyz.ineanto.noencryptionx.compatibility;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Logger;

public class CompatibilityProvider {
    @Getter
    private PacketChannelHandler packetChannelHandler;
    @Getter
    private PlayerChannel playerChannel;

    private final Logger logger = Logger.getLogger("NoEncryptionX/Compat");

    @Getter
    private final List<String> supportedVersions = List.of(
            "1.19",
            "1.19.1",
            "1.19.2",
            "1.19.3",
            "1.19.4",
            "1.20",
            "1.20.1",
            "1.20.2"
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