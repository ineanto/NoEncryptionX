package xyz.ineanto.noencryptionx.compatibility;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;
import org.bukkit.craftbukkit.v1_19_R2.util.CraftChatMessage;

import java.util.Optional;

public class PacketChannelHandler_1_19_R2 implements PacketChannelHandler {
    public Object writePacket(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise promise, boolean playerPipe) {
        if (playerPipe) {
            if (packet instanceof final ClientboundPlayerChatPacket clientboundPlayerChatPacket) {
                final Component chatMessage = Optional.ofNullable(clientboundPlayerChatPacket.unsignedContent()).orElse(Component.literal(clientboundPlayerChatPacket.body().content()));
                final Optional<ChatType.Bound> chatType = clientboundPlayerChatPacket.chatType().resolve(((CraftServer) Bukkit.getServer()).getServer().registryAccess());

                return new ClientboundSystemChatPacket(
                        chatType.orElseThrow().decorate(chatMessage),
                        false
                );
            }

            if (packet instanceof final ClientboundSystemChatPacket clientboundSystemChatPacket) {
                if (clientboundSystemChatPacket.content() == null) {
                    return clientboundSystemChatPacket;
                } else {
                    return new ClientboundSystemChatPacket(
                            CraftChatMessage.fromJSONOrNull(clientboundSystemChatPacket.content()),
                            clientboundSystemChatPacket.overlay());
                }
            }
        } else {
            if (packet instanceof final ClientboundServerDataPacket clientboundServerDataPacket) {
                // Get disable banner from config
                if (true) {
                    return new ClientboundServerDataPacket(
                            clientboundServerDataPacket.getMotd().orElse(null),
                            clientboundServerDataPacket.getIconBase64().orElse(null),
                            true
                    );
                }
            }
        }

        return packet;
    }
}