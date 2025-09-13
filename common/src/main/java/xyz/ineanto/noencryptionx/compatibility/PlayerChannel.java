package xyz.ineanto.noencryptionx.compatibility;

import io.netty.channel.Channel;
import org.bukkit.entity.Player;

import java.util.List;

public interface PlayerChannel {
    Channel getChannel(Player player);

    List<?> getServerConnections();
}
