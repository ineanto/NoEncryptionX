package xyz.ineanto.noencryptionx.compatibility;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import static java.util.Map.*;

public class CompatibilityProvider {
    @Getter
    private PacketChannelHandler packetChannelHandler;
    @Getter
    private PlayerChannel playerChannel;

    private final Logger logger = Logger.getLogger("NoEncryptionX/Compat");

    private final Map<Set<String>, CompatibilityPair<PlayerChannel, PacketChannelHandler>> versionsToCompatibility = ofEntries(
            entry(Set.of("1.19", "1.19.1", "1.19.2"), new CompatibilityPair<>(new PlayerChannel_1_19_R1(), new PacketChannelHandler_1_19_R1())),
            entry(Set.of("1.19.3"), new CompatibilityPair<>(new PlayerChannel_1_19_R2(), new PacketChannelHandler_1_19_R2())),
            entry(Set.of("1.19.4"), new CompatibilityPair<>(new PlayerChannel_1_19_R3(), new PacketChannelHandler_1_19_R3())),
            entry(Set.of("1.20", "1.20.1"), new CompatibilityPair<>(new PlayerChannel_1_20_R1(), new PacketChannelHandler_1_20_R1())),
            entry(Set.of("1.20.2"), new CompatibilityPair<>(new PlayerChannel_1_20_R2(), new PacketChannelHandler_1_20_R2())),
            entry(Set.of("1.20.3", "1.20.4"), new CompatibilityPair<>(new PlayerChannel_1_20_R3(), new PacketChannelHandler_1_20_R3())),
            entry(Set.of("1.20.5", "1.20.6"), new CompatibilityPair<>(new PlayerChannel_1_20_R4(), new PacketChannelHandler_1_20_R4())),
            entry(Set.of("1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4", "1.21.5", "1.21.7", "1.21.8", "1.21.9", "1.21.10", "1.21.11"), new CompatibilityPair<>(new PlayerChannel_1_21(), new PacketChannelHandler_1_21()))
    );

    public boolean setup() {
        final CompatibilityPair<PlayerChannel, PacketChannelHandler> compatibilityPair = getCompatibilityPair();

        if (compatibilityPair == null) {
            return false;
        }

        playerChannel = compatibilityPair.first();
        packetChannelHandler = compatibilityPair.second();

        logger.info("Loaded compatibility for " + Bukkit.getBukkitVersion());
        return true;
    }

    private CompatibilityPair<PlayerChannel, PacketChannelHandler> getCompatibilityPair() {
        // A bit complicated, but shouldn't be updated anytime soon so that's ok?
        final AtomicReference<CompatibilityPair<PlayerChannel, PacketChannelHandler>> tempPair = new AtomicReference<>(null);

        versionsToCompatibility.forEach((versions, compatibilityPair) ->
        {
            if (versions.stream().anyMatch(version -> version.equals(MinecraftVersion.getVersionStripped()))) {
                tempPair.set(compatibilityPair);
            }
        });

        return tempPair.get();
    }
}