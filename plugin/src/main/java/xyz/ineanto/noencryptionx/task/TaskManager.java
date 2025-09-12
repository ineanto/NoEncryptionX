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

    private final long collectionInterval = 20L * 60 * 30; // 30 minutes

    public TaskManager(NoEncryptionX instance) {
        this.instance = instance;
        this.unusedPlayerChannelCollector = new UnusedPlayerChannelCollector(instance);
        this.unusedServerChannelCollector = new UnusedServerChannelCollector(instance);
    }

    public void startTasks() {
        final BukkitTask unusedPlayerChannelCollectorTask = Bukkit.getScheduler().runTaskTimerAsynchronously(instance, unusedPlayerChannelCollector::collect, collectionInterval, collectionInterval);
        final BukkitTask unusedServerChannelCollectorTask = Bukkit.getScheduler().runTaskTimerAsynchronously(instance, unusedServerChannelCollector::collect, collectionInterval, collectionInterval);

        tasks.add(unusedPlayerChannelCollectorTask);
        tasks.add(unusedServerChannelCollectorTask);
    }

    public void stopTasks() {
        tasks.forEach(BukkitTask::cancel);
        tasks.clear();
    }
}