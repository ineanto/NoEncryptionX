package xyz.ineanto.noencryptionx;

import io.netty.channel.*;
import xyz.ineanto.noencryptionx.impl.Compatibility;
import xyz.ineanto.noencryptionx.config.ConfigurationHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin (PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        final Channel channel = Compatibility.COMPATIBLE_PLAYER.getChannel(player);
        final ChannelPipeline pipeline = channel.pipeline();
        final ChannelDuplexHandler handler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
                Object newPacket = Compatibility.COMPATIBLE_PACKET_LISTENER.readPacket(channelHandlerContext, packet);
                super.channelRead(channelHandlerContext, newPacket);
            }

            @Override
            public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise promise) throws Exception {
                Object newPacket = Compatibility.COMPATIBLE_PACKET_LISTENER.writePacket(channelHandlerContext, packet, promise);
                super.write(channelHandlerContext, newPacket, promise);
            }
        };

        if (pipeline.get("packet_handler") == null) {
            pipeline.addLast(NoEncryptionX.playerHandlerName, handler);
        } else {
            pipeline.addBefore("packet_handler", NoEncryptionX.playerHandlerName, handler);
        }

        if (ConfigurationHandler.Config.getLoginProtectionMessage() != null) {
            if (!ConfigurationHandler.Config.getLoginProtectionMessage().trim().equals("")) {
                Chat.sendChat(player, ConfigurationHandler.Config.getLoginProtectionMessage());
            }
        }

        NoEncryptionX.addPlayerChannel(channel);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit (PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        final Channel channel = Compatibility.COMPATIBLE_PLAYER.getChannel(player);

        try {
            channel.eventLoop().submit(() -> channel.pipeline().remove(NoEncryptionX.playerHandlerName));
        } catch (NullPointerException ex) {
            NoEncryptionX.logger().warning("Could not remove the player packet handler for " + player.getName() + " (" + player.getUniqueId() + ")");
            NoEncryptionX.logger().warning("This is not a fatal error, will be cleaned in garbage collection, and can be safely ignored");
        }
    }
}