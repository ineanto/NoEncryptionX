package xyz.ineanto.noencryptionx.utils;

import xyz.ineanto.noencryptionx.NoEncryptionX;
import xyz.ineanto.noencryptionx.config.ConfigurationHandler;

public class InternalMetrics {
    private static Metrics metrics;

    public static void loadMetrics() {
        if (!enabled())
            return;

        int pluginId = 17791;
        InternalMetrics.metrics = new Metrics(NoEncryptionX.plugin(), pluginId);
        insertChart(new Metrics.SimplePie("moduleType", () -> "NMS"));

        NoEncryptionX.logger().info("bStats is enabled for NoEncryption by default. To disable this, or to see more info, check the NoEncryption config");
    }

    public static void insertChart(Metrics.CustomChart chart) {
        if (!enabled())
            return;

        InternalMetrics.metrics.addCustomChart(chart);
    }

    private static boolean enabled() {
        return ConfigurationHandler.Config.bStatsEnabled();
    }
}
