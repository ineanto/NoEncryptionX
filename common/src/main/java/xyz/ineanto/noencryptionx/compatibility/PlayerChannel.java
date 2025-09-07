package xyz.ineanto.noencryptionx.compatibility;

import io.netty.channel.Channel;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class PlayerChannel {
    public abstract Channel getChannel(Player player) throws NoSuchFieldException, IllegalAccessException;

    public abstract List<?> getServerConnections();

    public abstract boolean setChannelFieldAccessible() throws NoSuchFieldException, IllegalAccessException;
}
