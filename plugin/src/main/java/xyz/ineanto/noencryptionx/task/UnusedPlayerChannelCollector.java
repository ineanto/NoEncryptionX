package xyz.ineanto.noencryptionx.task;

import xyz.ineanto.noencryptionx.NoEncryptionX;

public record UnusedPlayerChannelCollector(NoEncryptionX instance) {

    public void collect() {
        NoEncryptionX.activePlayerChannels.forEach(channel -> {
            if (!channel.isOpen()) {
                if (channel.pipeline().get(NoEncryptionX.playerHandlerName) != null) {
                    channel.pipeline().remove(NoEncryptionX.playerHandlerName);

                    NoEncryptionX.removePlayerChannel(channel);
                } else {
                    NoEncryptionX.removePlayerChannel(channel);
                }
            }
        });
    }
}
