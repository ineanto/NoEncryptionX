package xyz.ineanto.noencryptionx.injector;

import io.netty.channel.Channel;

import java.util.*;

public class ChannelManager {
    public static List<Channel> activePlayerChannels;

    // Added in 1.19.3
    public static Map<UUID, Channel> serverChannels;
    public static List<Channel> activeServerChannels;

    public ChannelManager() {
        activePlayerChannels = Collections.unmodifiableList(new ArrayList<>());
        activeServerChannels = Collections.unmodifiableList(new ArrayList<>());
        serverChannels = Collections.unmodifiableMap(new HashMap<>());
    }
}
