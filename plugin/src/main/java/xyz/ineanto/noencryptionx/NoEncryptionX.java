package xyz.ineanto.noencryptionx;

import io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import xyz.ineanto.noencryptionx.commands.MainCommand;
import xyz.ineanto.noencryptionx.config.ConfigurationHandler;
import xyz.ineanto.noencryptionx.impl.Compatibility;
import xyz.ineanto.noencryptionx.utils.FileMgmt;
import xyz.ineanto.noencryptionx.utils.InternalMetrics;
import xyz.ineanto.noencryptionx.utils.PlayerChannelGC;
import xyz.ineanto.noencryptionx.utils.updates.UpdateChecker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public final class NoEncryptionX extends JavaPlugin {
    private static NoEncryptionX plugin;
    private static Logger logger;
    public static List<Channel> activePlayerChannels;

    private static BukkitTask playerGarbageCollectorTask;

    public static final String playerHandlerName = "noencryption_playerlevel";

    @Override
    public void onEnable() {
        plugin = this;
        logger = getLogger();
        activePlayerChannels = Collections.unmodifiableList(new ArrayList<>());

        if (Compatibility.SERVER_COMPATIBLE) {
            FileMgmt.initialize(this);
            ConfigurationHandler.initialize(this);

            if (!ConfigurationHandler.loadSettings()) {
                logger().severe("Configuration could not be loaded, disabling...");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }

            ConfigurationHandler.Config.printChanges();
            ConfigurationHandler.Notices.loadAndPrintChanges();

            getCommand("noencryption").setExecutor(new MainCommand());
            getCommand("noencryption").setTabCompleter(new MainCommand());

            startTasks();

            Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

            if (ConfigurationHandler.Config.doAutoUpdateCheck())
                UpdateChecker.check(
                        () -> {
                            logger().info("You are running an old version of NoEncryption.");
                            logger().info("It is recommended to update to the latest version");
                            logger().info("for the best experience. The update can be found here:");
                            logger().info(UpdateChecker.updateUrl.toString());
                        },
                        () -> {
                            logger().info("Your NoEncryption version is up-to-date");
                        },
                        () -> {
                            logger().info("Could not check for the latest version of NoEncryption.");
                            logger().info("It is recommended to update to the latest version");
                            logger().info("for the best experience. The update can be found here:");
                            logger().info(UpdateChecker.updateUrl.toString());
                        }
                );

            logger().info("Compatibility successful!");
            logger().info("If you used /reload to update NoEncryption, your players need to disconnect and join back");

            if (Bukkit.getPluginManager().getPlugin("Essentials") != null) {
                logger().info("=====================================================================");
                logger().info("We are aware of the Essentials warning about severe issues.");
                logger().info("Currently, there are no known issues relating to NoEncryption and Essentials.");
                logger().info("If you encounter any issues, please create an issue on the NoEncryption GitHub at");
                logger().info("https://github.com/Doclic/NoEncryption/issues");
            }

            if (Bukkit.getPluginManager().getPlugin("ViaVersion") != null) {
                logger().info("=====================================================================");
                logger().info("We are aware of the ViaVersion warning about severe issues.");
                logger().info("Currently, there are no known issues relating to NoEncryption and ViaVersion.");
                logger().info("If you encounter any issues, please create an issue on the NoEncryption GitHub at");
                logger().info("https://github.com/Doclic/NoEncryption/issues");
            }

            InternalMetrics.loadMetrics();
        } else {
            logger().severe("Failed to setup NoEncryption's compatibility!");
            logger().severe("Your server version (" + Compatibility.SERVER_VERSION + ") is not compatible with this JAR! Check here for the latest version: https://github.com/Doclic/NoEncryption/releases/latest");

            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        stopTasks();
    }

    private static void startTasks() {
        playerGarbageCollectorTask = PlayerChannelGC.start();
    }

    private static void stopTasks() {
        PlayerChannelGC.stop(playerGarbageCollectorTask);
    }

    public static NoEncryptionX plugin() {
        return plugin;
    }

    public static Logger logger() {
        return logger;
    }

    public String getRootFolder() {
        return this.getDataFolder().getPath();
    }

    public static boolean usesKyoriChat() {
        try {
            Class.forName("net.kyori.adventure.Adventure");

            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void addPlayerChannel(Channel channel) {
        List<Channel> modified = new ArrayList<>(activePlayerChannels);
        modified.add(channel);
        activePlayerChannels = Collections.unmodifiableList(modified);
    }

    public static void removePlayerChannel(Channel channel) {
        List<Channel> modified = new ArrayList<>(activePlayerChannels);
        modified.remove(channel);
        activePlayerChannels = Collections.unmodifiableList(modified);
    }
}
