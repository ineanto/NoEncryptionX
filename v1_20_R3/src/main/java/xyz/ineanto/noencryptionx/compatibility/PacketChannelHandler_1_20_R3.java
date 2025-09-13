package xyz.ineanto.noencryptionx.compatibility;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.craftbukkit.v1_20_R3.util.CraftChatMessage;

import java.util.Optional;

public class PacketChannelHandler_1_20_R3 implements PacketChannelHandler {
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

            if (packet instanceof ClientboundSystemChatPacket(Component content, boolean overlay)) {
                return new ClientboundSystemChatPacket(
                        CraftChatMessage.fromJSONOrNull(content.getString()),
                        overlay);
            }
        } else {
            if (packet instanceof final ClientboundServerDataPacket clientboundServerDataPacket) {
                // Get disable banner from config
                if (true) {
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
