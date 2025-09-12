package xyz.ineanto.noencryptionx.event;

import io.netty.channel.Channel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ineanto.noencryptionx.NoEncryptionX;

public class PlayerQuitListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent e) throws NoSuchFieldException, IllegalAccessException {
        final Player player = e.getPlayer();
        final Channel channel = NoEncryptionX.getInstance().getCompatibility().getPlayerChannel().getChannel(player);

        try {
            channel.eventLoop().submit(() -> channel.pipeline().remove(NoEncryptionX.playerHandlerName));
        } catch (NullPointerException ex) {
            NoEncryptionX.getInstance().getLogger().warning("Could not remove the player packet handler for " + player.getName() + " (" + player.getUniqueId() + ")");
            NoEncryptionX.getInstance().getLogger().warning("This is not a fatal error, will be cleaned in garbage collection, and can be safely ignored");
        }
    }
}
