package xyz.ineanto.noencryptionx.compatibility;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.util.Crypt.SaltSignaturePair;

import java.util.Optional;

public class PacketChannelHandler_1_19 implements PacketChannelHandler {
    @Override
    public Object writePacket(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise promise, boolean playerPipe) {
        if (packet instanceof final ClientboundPlayerChatPacket clientboundPlayerChatPacket) {
            final Optional<Component> unsignedContent = clientboundPlayerChatPacket.unsignedContent();

            return new ClientboundPlayerChatPacket(
                    unsignedContent.orElse(clientboundPlayerChatPacket.signedContent()), // use unsigned content if available, this is the signed content field
                    unsignedContent, // unsigned content field
                    clientboundPlayerChatPacket.typeId(),
                    clientboundPlayerChatPacket.sender(),
                    clientboundPlayerChatPacket.timeStamp(),
                    new SaltSignaturePair(0, new byte[0])); // salt signature field
        }

        return packet;
    }
}