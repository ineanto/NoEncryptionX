package xyz.ineanto.noencryptionx.compatibility;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.bukkit.craftbukkit.util.CraftChatMessage;

import java.util.Optional;

public class PacketChannelHandler_1_21 implements PacketChannelHandler {
    public Object writePacket(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise promise, boolean playerPipe) {
        if (playerPipe) {
            if (packet instanceof final ClientboundPlayerChatPacket clientboundPlayerChatPacket) {
                final Component chatMessage = Optional.ofNullable(clientboundPlayerChatPacket.unsignedContent()).orElse(Component.literal(clientboundPlayerChatPacket.body().content()));
                final ChatType.Bound chatType = clientboundPlayerChatPacket.chatType();

                return new ClientboundSystemChatPacket(
                        chatType.decorate(chatMessage),
                        false
                );
            }

            if (packet instanceof ClientboundSystemChatPacket(Component content, boolean overlay)) {
                return new ClientboundSystemChatPacket(
                        CraftChatMessage.fromJSONOrNull(content.getString()),
                        overlay);
            }
        } else {
            if (packet instanceof ClientboundServerDataPacket(Component motd, Optional<byte[]> iconBytes)) {
                if (true) {
                    // recreate a new packet
                    return new ClientboundServerDataPacket(
                            motd,
                            iconBytes
                    );
                }
            }
        }

        return packet;
    }
}
