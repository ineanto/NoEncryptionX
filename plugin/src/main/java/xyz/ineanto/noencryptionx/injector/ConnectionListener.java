//package xyz.ineanto.noencryptionx;
//
//import io.netty.channel.*;
//import org.bukkit.Bukkit;
//import org.bukkit.scheduler.BukkitTask;
//import xyz.ineanto.noencryptionx.compatibility.Compatibility;
//
//public class ConnectionListener {
//    public static BukkitTask start() {
//        return Bukkit.getScheduler().runTaskTimerAsynchronously(NoEncryptionX.plugin(), ConnectionListener::runCatch, 1, 1);
//    }
//
//    public static void stop(BukkitTask task) {
//        if (task != null)
//            task.cancel();
//    }
//
//    private static void runCatch() {
//        Compatibility.PLAYER_CHANNEL.getServerConnections().forEach(connection -> {
//            Channel channel = connection.channel;
//            ChannelPipeline pipeline = channel.pipeline();
//
//            if (pipeline.get(NoEncryptionX.serverHandlerName) == null) {
//                final ChannelDuplexHandler handler = new ChannelDuplexHandler() {
//                    @Override
//                    public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
//                        Object newPacket = Compatibility.PACKET_CHANNEL_HANDLER.readPacket(channelHandlerContext, packet);
//                        super.channelRead(channelHandlerContext, newPacket);
//                    }
//
//                    @Override
//                    public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise promise) throws Exception {
//                        this.handleConnectionChannel(packet);
//
//                        Object newPacket = Compatibility.COMPATIBLE_PACKET_LISTENER.writePacket(channelHandlerContext, packet, promise, false);
//                        super.write(channelHandlerContext, newPacket, promise);
//                    }
//
//                    public void handleConnectionChannel(Object packet) {
//                        if (packet instanceof ClientboundGameProfilePacket clientboundGameProfilePacket) {
//                            NoEncryptionX.serverChannels.put(clientboundGameProfilePacket.getGameProfile().getId(), channel);
//                        }
//                    }
//                };
//
//                if (pipeline.get("packet_handler") == null) {
//                    pipeline.addLast(NoEncryption.serverHandlerName, handler);
//                } else {
//                    pipeline.addBefore("packet_handler", NoEncryption.serverHandlerName, handler);
//                }
//
//                NoEncryption.addServerChannel(channel);
//            }
//        });
//    }
//}
//
