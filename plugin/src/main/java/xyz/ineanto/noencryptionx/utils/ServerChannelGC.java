//package xyz.ineanto.noencryptionx.utils;
//
//import org.bukkit.Bukkit;
//import org.bukkit.scheduler.BukkitTask;
//import xyz.ineanto.noencryptionx.NoEncryptionX;
//
//public class ServerChannelGC {
//    public static BukkitTask start() {
//        return Bukkit.getScheduler().runTaskTimerAsynchronously(NoEncryptionX.plugin(), ServerChannelGC::runGC, 100, 100);
//    }
//
//    public static void stop(BukkitTask task) {
//        if (task != null)
//            task.cancel();
//    }
//
//    private static void runGC() {
//        NoEncryptionX.serverChannels.forEach((uuid, channel) -> {
//            if (Bukkit.getPlayer(uuid) == null) {
//                channel.eventLoop().submit(() -> channel.pipeline().remove(NoEncryptionX.serverHandlerName));
//                NoEncryptionX.serverChannels.remove(uuid);
//            }
//        });
//
//        NoEncryptionX.activeServerChannels.forEach(channel -> {
//            if (!channel.isOpen()) {
//                if (channel.pipeline().get(NoEncryption.serverHandlerName) != null) {
//                    channel.pipeline().remove(NoEncryption.serverHandlerName);
//
//                    NoEncryption.removeServerChannel(channel);
//                } else {
//                    NoEncryption.removeServerChannel(channel);;
//                }
//            }
//        });
//    }
//}
