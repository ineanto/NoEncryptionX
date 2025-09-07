package xyz.ineanto.noencryptionx.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import xyz.ineanto.noencryptionx.compatibility.PacketChannelHandler;
import xyz.ineanto.noencryptionx.config.ConfigurationHandler;
import xyz.ineanto.noencryptionx.utils.InternalMetrics;
import xyz.ineanto.noencryptionx.utils.Metrics;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;

import java.util.Optional;

public class PacketChannelHandler_1_19_4 implements PacketChannelHandler {
    public Object writePacket(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise promise, boolean playerPipe) throws Exception {
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
                    // recreate a new packet
                    return new ClientboundSystemChatPacket(
                            CraftChatMessage.fromJSONOrNull(clientboundSystemChatPacket.content()),
                            clientboundSystemChatPacket.overlay());
                }
            }
        } else {
            if (packet instanceof final ClientboundServerDataPacket clientboundServerDataPacket) {
                if (ConfigurationHandler.Config.getDisableBanner()) {
                    // recreate a new packet
                    return new ClientboundServerDataPacket(
                            clientboundServerDataPacket.getMotd(),
                            clientboundServerDataPacket.getIconBytes(),
                            true
                    );
                }
            }
        }

        return packet;
    }
}