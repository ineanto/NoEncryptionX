package xyz.ineanto.noencryptionx.compatibility;

import io.netty.channel.Channel;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlayerChannel_1_19_2 {
    public Channel getChannel(Player player) {
        return ((CraftPlayer) player).getHandle().connection.connection.channel;
    }
}