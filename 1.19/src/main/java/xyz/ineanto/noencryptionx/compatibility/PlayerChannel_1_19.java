package xyz.ineanto.noencryptionx.compatibility;

import io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class PlayerChannel_1_19 extends PlayerChannel {
    public Channel getChannel(Player player) {
        return ((CraftPlayer) player).getHandle().connection.connection.channel;
    }

    @Override
    public List<?> getServerConnections() {
        return Objects.requireNonNull(((CraftServer) Bukkit.getServer()).getServer().getConnection()).getConnections();
    }

    @Override
    public boolean setChannelFieldAccessible() throws NoSuchFieldException, IllegalAccessException {
        return false;
    }
}