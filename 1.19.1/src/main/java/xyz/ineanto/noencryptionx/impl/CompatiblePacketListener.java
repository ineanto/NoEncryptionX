package xyz.ineanto.noencryptionx.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import xyz.ineanto.noencryptionx.config.ConfigurationHandler;
import xyz.ineanto.noencryptionx.utils.InternalMetrics;
import xyz.ineanto.noencryptionx.utils.Metrics;
import net.minecraft.network.chat.*;
import net.minecraft.network.protocol.game.ClientboundPlayerChatHeaderPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftChatMessage;

import java.util.Optional;
import java.util.UUID;

public class CompatiblePacketListener {
    public Object readPacket(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception { return packet; }

    public Object writePacket(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise promise) throws Exception {
        if (packet instanceof final ClientboundPlayerChatPacket clientboundPlayerChatPacket) {
            final PlayerChatMessage message = clientboundPlayerChatPacket.message();
            final Optional<Component> unsignedContent = message.unsignedContent();
            final ChatMessageContent signedContent = message.signedContent();
            final SignedMessageBody signedBody = message.signedBody();
            final ChatType.BoundNetwork chatType = clientboundPlayerChatPacket.chatType();

            InternalMetrics.insertChart(new Metrics.SingleLineChart("strippedMessages", () -> 1));

            // recreate a new packet
            return new ClientboundPlayerChatPacket(
                    new PlayerChatMessage(
                            new SignedMessageHeader(
                                    new MessageSignature(new byte[0]),
                                    (ConfigurationHandler.Config.getForwardUUID() ? clientboundPlayerChatPacket.message().signedHeader().sender() : new UUID(0, 0))),
                            new MessageSignature(new byte[0]),
                            new SignedMessageBody(
                                    new ChatMessageContent(
                                            signedContent.plain(),
                                            signedContent.decorated()),
                                    signedBody.timeStamp(),
                                    0,
                                    signedBody.lastSeen()),
                            unsignedContent,
                            new FilterMask(0)
                    ),
                    chatType);
        }

        if (packet instanceof final ClientboundSystemChatPacket clientboundSystemChatPacket) {
            if (clientboundSystemChatPacket.content() == null) {
                return clientboundSystemChatPacket;
            } else {
                InternalMetrics.insertChart(new Metrics.SingleLineChart("strippedMessages", () -> 1));

                // recreate a new packet
                return new ClientboundSystemChatPacket(
                        CraftChatMessage.fromJSONOrNull(clientboundSystemChatPacket.content()),
                        clientboundSystemChatPacket.overlay());
            }
        }

        if (packet instanceof final ClientboundPlayerChatHeaderPacket clientboundPlayerChatHeaderPacket) {
            // recreate a new packet
            return new ClientboundPlayerChatHeaderPacket(
                    new SignedMessageHeader(
                            new MessageSignature(new byte[0]),
                            new UUID(0, 0)),
                    new MessageSignature(new byte[0]),
                    clientboundPlayerChatHeaderPacket.bodyDigest()
            );
        }

        if (packet instanceof final ClientboundServerDataPacket clientboundServerDataPacket) {
            InternalMetrics.insertChart(new Metrics.SingleLineChart("popupsBlocked", () -> 1));

            if (ConfigurationHandler.Config.getDisableBanner()) {
                // recreate a new packet
                return new ClientboundServerDataPacket(
                        clientboundServerDataPacket.getMotd().orElse(null),
                        clientboundServerDataPacket.getIconBase64().orElse(null),
                        clientboundServerDataPacket.previewsChat(),
                        true
                );
            }
        }

        return packet;
    }
}