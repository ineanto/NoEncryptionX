package xyz.ineanto.noencryptionx.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import xyz.ineanto.noencryptionx.NoEncryptionX;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TaskManager {
    private final NoEncryptionX instance;

    private final Collection<BukkitTask> tasks = Collections.synchronizedList(new ArrayList<>());
    private final UnusedPlayerChannelCollector unusedPlayerChannelCollector;
    private final UnusedServerChannelCollector unusedServerChannelCollector;

    private final long COLLECTION_INTERVAL = 20L * 60 * 30; // 30 minutes

    public TaskManager(NoEncryptionX instance) {
        this.instance = instance;
        this.unusedPlayerChannelCollector = new UnusedPlayerChannelCollector(instance);
        this.unusedServerChannelCollector = new UnusedServerChannelCollector(instance);
    }

    public void startTasks() {
        final BukkitTask unusedPlayerChannelCollectorTask = Bukkit.getScheduler().runTaskTimerAsynchronously(instance, unusedPlayerChannelCollector::collect, COLLECTION_INTERVAL, COLLECTION_INTERVAL);
        final BukkitTask unusedServerChannelCollectorTask = Bukkit.getScheduler().runTaskTimerAsynchronously(instance, unusedServerChannelCollector::collect, COLLECTION_INTERVAL, COLLECTION_INTERVAL);

        tasks.add(unusedPlayerChannelCollectorTask);
        tasks.add(unusedServerChannelCollectorTask);
    }

    public void stopTasks() {
        // TODO (Ineanto, 08/09/2025): stop tasks
    }
}