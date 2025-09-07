package xyz.ineanto.noencryptionx.compatibility;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public interface PacketChannelHandler {
    default Object readPacket(ChannelHandlerContext channelHandlerContext, Object packet) { return packet; }

    Object writePacket(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise promise, boolean playerPipe);
}
