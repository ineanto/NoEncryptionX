package xyz.ineanto.noencryptionx.compatibility;

import io.netty.channel.Channel;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PlayerChannel_1_19_4 {
    public Channel getChannel(Player player) throws NoSuchFieldException, IllegalAccessException {
        ServerPlayer playerHandle = ((CraftPlayer) player).getHandle();
        ServerGamePacketListenerImpl nmsPacketListener = playerHandle.connection;

        /*
         * 1.19.4 mappings:
         *
         * net.minecraft.server.network.ServerGamePacketListenerImpl -> aji:
         *     net.minecraft.network.Connection connection -> h
         */
        Field connectionField = nmsPacketListener.getClass().getDeclaredField("h");
        Object result = connectionField.get(nmsPacketListener);

        return ((Connection) result).channel;
    }

    public List<Connection> getServerConnections() {
        try {
            return Objects.requireNonNull(((CraftServer) Bukkit.getServer()).getServer().getConnection()).getConnections();
        } catch (NullPointerException e) {
            return Collections.emptyList();
        }
    }

    public boolean unlockChannelField() {
        Class<?> focus = ServerGamePacketListenerImpl.class;

        try {
            /*
             * 1.19.4 mappings:
             *
             * net.minecraft.server.network.ServerGamePacketListenerImpl -> aji:
             *     net.minecraft.network.Connection connection -> h
             */

            focus.getDeclaredField("h").setAccessible(true);
            return true;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
    }
}