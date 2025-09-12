package xyz.ineanto.noencryptionx;

import io.netty.channel.Channel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ineanto.noencryptionx.commands.MainCommand;
import xyz.ineanto.noencryptionx.compatibility.CompatibilityProvider;
import xyz.ineanto.noencryptionx.config.FileMgmt;
import xyz.ineanto.noencryptionx.event.PlayerJoinListener;
import xyz.ineanto.noencryptionx.event.PlayerQuitListener;
import xyz.ineanto.noencryptionx.task.TaskManager;

import java.util.*;

public final class NoEncryptionX extends JavaPlugin {
    @Getter
    private static NoEncryptionX instance;

    @Getter
    private final CompatibilityProvider compatibility = new CompatibilityProvider();
    private final TaskManager taskManager = new TaskManager(this);

    public static Map<UUID, Channel> serverChannels;
    public static List<Channel> activePlayerChannels;
    public static List<Channel> activeServerChannels;

    public static final String playerHandlerName = "noencryption_playerlevel";
    public static final String serverHandlerName = "noencryption_serverlevel";

    @Override
    public void onEnable() {
        instance = this;

        activePlayerChannels = Collections.unmodifiableList(new ArrayList<>());
        activeServerChannels = Collections.unmodifiableList(new ArrayList<>());

        compatibility.setup();

        if (true) { // TODO (Ineanto, 08/09/2025):  check if server is compatible
            FileMgmt.initialize(this);
//            Configuration.initialize(this);
//
//            if (!Configuration.loadSettings()) {
//                getLogger().severe("Configuration could not be loaded, disabling...");
//                Bukkit.getPluginManager().disablePlugin(this);
//                return;
//            }
//
//            Configuration.Config.printChanges();
//            Configuration.Notices.loadAndPrintChanges();

            getCommand("noencryption").setExecutor(new MainCommand());
            getCommand("noencryption").setTabCompleter(new MainCommand());

            taskManager.startTasks();

            Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
            Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);

            if (true) {
                // TODO (Ineanto, 08/09/2025):  check for auto update
//                UpdateChecker.check(
//                        () -> {
//                            getLogger().info("You are running an old version of NoEncryption.");
//                            getLogger().info("It is recommended to update to the latest version");
//                            getLogger().info("for the best experience. The update can be found here:");
//                            getLogger().info(UpdateChecker.updateUrl.toString());
//                        },
//                        () -> {
//                            getLogger().info("Your NoEncryption version is up-to-date");
//                        },
//                        () -> {
//                            getLogger().info("Could not check for the latest version of NoEncryption.");
//                            getLogger().info("It is recommended to update to the latest version");
//                            getLogger().info("for the best experience. The update can be found here:");
//                            getLogger().info(UpdateChecker.updateUrl.toString());
//                        }
//                );
            }

            getLogger().info("Compatibility successful!");
            getLogger().info("If you used /reload to update NoEncryption, your players need to disconnect and join back");

            if (Bukkit.getPluginManager().getPlugin("Essentials") != null) {
                getLogger().info("=====================================================================");
                getLogger().info("We are aware of the Essentials warning about severe issues.");
                getLogger().info("Currently, there are no known issues relating to NoEncryption and Essentials.");
                getLogger().info("If you encounter any issues, please create an issue on the NoEncryption GitHub at");
                getLogger().info("https://github.com/Doclic/NoEncryption/issues");
            }

            if (Bukkit.getPluginManager().getPlugin("ViaVersion") != null) {
                getLogger().info("=====================================================================");
                getLogger().info("We are aware of the ViaVersion warning about severe issues.");
                getLogger().info("Currently, there are no known issues relating to NoEncryption and ViaVersion.");
                getLogger().info("If you encounter any issues, please create an issue on the NoEncryption GitHub at");
                getLogger().info("https://github.com/Doclic/NoEncryption/issues");
            }

            //InternalMetrics.loadMetrics();
        } else {
            getLogger().severe("Failed to setup NoEncryption's compatibility!");
            getLogger().severe("Your server version (" + compatibility.getVersionStripped() + ") is not compatible with this JAR! Check here for the latest version: https://github.com/Doclic/NoEncryption/releases/latest");

            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        taskManager.stopTasks();
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

    //.............
    // ADDED IN 1.19.3
    //.............

    public static void addServerChannel(Channel channel) {
        List<Channel> modified = new ArrayList<>(activeServerChannels);
        modified.add(channel);
        activeServerChannels = Collections.unmodifiableList(modified);
    }

    public static void removeServerChannel(Channel channel) {
        List<Channel> modified = new ArrayList<>(activeServerChannels);
        modified.remove(channel);
        activeServerChannels = Collections.unmodifiableList(modified);
    }
}
