package xyz.ineanto.noencryptionx.task;

import org.bukkit.Bukkit;
import xyz.ineanto.noencryptionx.NoEncryptionX;

public record UnusedServerChannelCollector(NoEncryptionX instance) {
    public void collect() {
        NoEncryptionX.serverChannels.forEach((uuid, channel) -> {
            if (Bukkit.getPlayer(uuid) == null) {
                channel.eventLoop().submit(() -> channel.pipeline().remove(NoEncryptionX.serverHandlerName));
                NoEncryptionX.serverChannels.remove(uuid);
            }
        });

        NoEncryptionX.activeServerChannels.forEach(channel -> {
            if (!channel.isOpen()) {
                if (channel.pipeline().get(NoEncryptionX.serverHandlerName) != null) {
                    channel.pipeline().remove(NoEncryptionX.serverHandlerName);

                    NoEncryptionX.removeServerChannel(channel);
                } else {
                    NoEncryptionX.removeServerChannel(channel);
                }
            }
        });
    }
}
