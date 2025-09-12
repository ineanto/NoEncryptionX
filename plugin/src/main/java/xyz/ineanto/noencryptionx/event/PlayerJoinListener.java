package xyz.ineanto.noencryptionx.event;

import io.netty.channel.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.ineanto.noencryptionx.NoEncryptionX;
import xyz.ineanto.noencryptionx.compatibility.CompatibilityProvider;

public class PlayerJoinListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) throws NoSuchFieldException, IllegalAccessException {
        final Player player = e.getPlayer();
        final CompatibilityProvider compatibility = NoEncryptionX.getInstance().getCompatibility();

        final Channel channel = compatibility.getPlayerChannel().getChannel(player);
        final ChannelPipeline pipeline = channel.pipeline();
        final ChannelDuplexHandler handler = new ChannelDuplexHandler() {
            @Override
            public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise promise) throws Exception {
                Object newPacket = compatibility.getPacketChannelHandler().writePacket(channelHandlerContext, packet, promise, true);
                super.write(channelHandlerContext, newPacket, promise);
            }
        };

        if (pipeline.get("packet_handler") == null) {
            pipeline.addLast(NoEncryptionX.playerHandlerName, handler);
        } else {
            pipeline.addBefore("packet_handler", NoEncryptionX.playerHandlerName, handler);
        }

//        if (Configuration.Config.getLoginProtectionMessage() != null) {
//            if (!Configuration.Config.getLoginProtectionMessage().trim().equals("")) {
//                Chat.sendChat(player, Configuration.Config.getLoginProtectionMessage());
//            }
//        }

        NoEncryptionX.addPlayerChannel(channel);
    }
}
