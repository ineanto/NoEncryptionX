package xyz.ineanto.noencryptionx.compatibility;

import io.netty.channel.Channel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerConnectionListener;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerChannel_1_20_2 implements PlayerChannel {
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
