package xyz.ineanto.noencryptionx.compatibility;

import org.bukkit.Bukkit;
import xyz.ineanto.noencryptionx.NoEncryptionX;
import xyz.ineanto.noencryptionx.impl.PacketChannelHandler_1_20_1;
import xyz.ineanto.noencryptionx.impl.PlayerChannel_1_20_1;

public class Compatibility {
    public static final PlayerChannel PLAYER_CHANNEL;
    public static final PacketChannelHandler PACKET_CHANNEL_HANDLER;
    public static final String PLUGIN_COMPATIBLE_VERSION;
    public static final String SERVER_VERSION;

    static {
        PLAYER_CHANNEL = new PlayerChannel_1_20_1();
        PACKET_CHANNEL_HANDLER = new PacketChannelHandler_1_20_1();

        String minecraftVersion;

        PLUGIN_COMPATIBLE_VERSION = "1.28.1-R0.1-SNAPSHOT";

        try {
            minecraftVersion = Bukkit.getBukkitVersion();
        } catch (ArrayIndexOutOfBoundsException exception) {
            minecraftVersion = null;
        }

        SERVER_VERSION = minecraftVersion;

        NoEncryptionX.logger().info("Your server is running version " + minecraftVersion);

        if (minecraftVersion.equals(PLUGIN_COMPATIBLE_VERSION)) {
            try {
                PLAYER_CHANNEL.setChannelFieldAccessible();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
