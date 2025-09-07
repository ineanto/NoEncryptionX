package xyz.ineanto.noencryptionx.utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import xyz.ineanto.noencryptionx.NoEncryptionX;

public class PlayerChannelGC {
    public static BukkitTask start() {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(NoEncryptionX.plugin(), PlayerChannelGC::runGC, 100, 100);
    }

    public static void stop(BukkitTask task) {
        if (task != null)
            task.cancel();
    }

    private static void runGC() {
        NoEncryptionX.activePlayerChannels.forEach(channel -> {
            if (!channel.isOpen()) {
                if (channel.pipeline().get(NoEncryptionX.playerHandlerName) != null) {
                    channel.pipeline().remove(NoEncryptionX.playerHandlerName);

                    NoEncryptionX.removePlayerChannel(channel);
                } else {
                    NoEncryptionX.removePlayerChannel(channel);;
                }
            }
        });
    }
}
