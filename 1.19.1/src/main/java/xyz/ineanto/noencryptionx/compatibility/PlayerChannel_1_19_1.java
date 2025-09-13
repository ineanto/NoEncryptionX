package xyz.ineanto.noencryptionx.compatibility;

import io.netty.channel.Channel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerConnectionListener;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class PlayerChannel_1_19_1 implements PlayerChannel {
    @Override
    public Channel getChannel(Player player) {
        final ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        return serverPlayer.connection.connection.channel;
    }

    @Override
    public List<?> getServerConnections() {
        final ServerConnectionListener connectionListener = ((CraftServer) Bukkit.getServer()).getServer().getConnection();

        if(connectionListener != null) {
            return connectionListener.getConnections();
        }

        return Collections.emptyList();
    }
}