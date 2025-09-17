package xyz.ineanto.noencryptionx.compatibility;

import io.netty.channel.Channel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerConnectionListener;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerChannel_1_21 implements PlayerChannel {
    @Override
    public Channel getChannel(Player player) {
        final ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        return serverPlayer.connection.connection.channel;
    }

    public List<?> getServerConnections() {
        final ServerConnectionListener connectionListener = ((CraftServer) Bukkit.getServer()).getServer().getConnection();
        return connectionListener.getConnections();
    }
}
