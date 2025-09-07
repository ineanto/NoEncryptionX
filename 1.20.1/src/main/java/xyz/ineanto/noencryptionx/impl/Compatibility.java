package xyz.ineanto.noencryptionx.impl;

import me.doclic.noencryption.NoEncryption;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Compatibility {
    public static final CompatiblePlayer COMPATIBLE_PLAYER;
    public static final CompatiblePacketListener COMPATIBLE_PACKET_LISTENER;
    public static final String PLUGIN_COMPATIBLE_VERSION;
    public static final boolean SERVER_COMPATIBLE;
    public static final String SERVER_VERSION;

    static {
        String minecraftVersion;

        PLUGIN_COMPATIBLE_VERSION = "1.20.1-R0.1-SNAPSHOT";

        try {
            minecraftVersion = Bukkit.getBukkitVersion();
        } catch (ArrayIndexOutOfBoundsException exception) {
            minecraftVersion = null;
        }

        SERVER_VERSION = minecraftVersion;

        NoEncryption.logger().info("Your server is running version " + minecraftVersion);

        if (!new CompatiblePlayer().unlockChannelField()) {
            NoEncryption.logger().severe("There was an error while unlocking a field for use with NoEncryption");

            minecraftVersion = null;
        }

        if (minecraftVersion != null && minecraftVersion.equals(PLUGIN_COMPATIBLE_VERSION)) {
            COMPATIBLE_PLAYER = instantiate(CompatiblePlayer.class);
            COMPATIBLE_PACKET_LISTENER = instantiate(CompatiblePacketListener.class);

            SERVER_COMPATIBLE = true;
        } else {
            COMPATIBLE_PLAYER = null;
            COMPATIBLE_PACKET_LISTENER = null;

            SERVER_COMPATIBLE = false;
        }
    }

    private static <T> T instantiate(Class<T> clazz) {
        if (clazz == null) return null;

        try {
            final Constructor<T> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
