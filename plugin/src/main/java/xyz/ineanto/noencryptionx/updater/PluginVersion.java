package xyz.ineanto.noencryptionx.updater;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import xyz.ineanto.noencryptionx.NoEncryptionX;

public class PluginVersion implements Comparable<PluginVersion> {
    @Getter
    private int major;

    @Getter
    private int minor;

    @Getter
    private int revision;

    public PluginVersion(int major, int minor, int revision) {
        this.major = major;
        this.minor = minor;
        this.revision = revision;

        if (!toString().matches("[0-9]+(\\.[0-9]+)*")) {
            throw new IllegalArgumentException("Invalid version format");
        }
    }

    public PluginVersion() { this(0, 0, 0); }

    public PluginVersion fromString(String version) {
        final String[] parts = version.split("\\.");
        this.major = parts.length > 0 ? Integer.parseInt(parts[0]) : 0;
        this.minor = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
        this.revision = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
        return this;
    }

    public PluginVersion getCurrent() {
        return fromString(NoEncryptionX.getInstance().getDescription().getVersion());
    }

    @Override
    public @NotNull String toString() {
        return String.format("%d.%d.%d", major, minor, revision);
    }

    @Override
    public int compareTo(@NotNull PluginVersion that) {
        if (this.major != that.major) {
            return Integer.compare(this.major, that.major);
        }

        if (this.minor != that.minor) {
            return Integer.compare(this.minor, that.minor);
        }

        return Integer.compare(this.revision, that.revision);
    }
}
