package ru.otus.hw_4;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.ListenerNotFoundException;
import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stas on 26.04.17.
 */
public class GCMonitor {

    private static final long KILOBYTE = 1024;
    private static final long MEGABYTE = KILOBYTE * 1024;
    private static final long GIGABYTE = MEGABYTE * 1024;
    private static final long SECOND = 1000;
    private static final long MINUTE = 60*SECOND;


    private Map<String, MemoryRegion> regions;
    private long start = System.currentTimeMillis();

    private static class MemoryRegion {

        private boolean heap;
        private String name;

        public MemoryRegion(String name, boolean heap) {
            this.heap = heap;
            this.name = name;
        }

        public boolean isHeap() {
            return heap;
        }

        public String getName() {
            return name;
        }

    }

    public GCMonitor() {
        regions = new HashMap<>(ManagementFactory.getMemoryPoolMXBeans().size());
        for(MemoryPoolMXBean mBean: ManagementFactory.getMemoryPoolMXBeans()) {
            regions.put(mBean.getName(), new MemoryRegion(mBean.getName(), mBean.getType() == MemoryType.HEAP));
        }
    }

    private NotificationListener gcHandler = new NotificationListener() {
        @Override
        public void handleNotification(Notification notification, Object handback) {
            if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                GarbageCollectionNotificationInfo gcInfo = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                Map<String, MemoryUsage> beforeGc = gcInfo.getGcInfo().getMemoryUsageBeforeGc();
                Map<String, MemoryUsage> afterGc = gcInfo.getGcInfo().getMemoryUsageAfterGc();
                StringBuilder sb = new StringBuilder();
                sb.append("\nUptime: ");
                appendTime(sb, System.currentTimeMillis() - start);
                sb.append("\nAction: ").append(gcInfo.getGcAction())
                        .append("\nCause: ").append(gcInfo.getGcCause())
                        .append("\nGCName: ").append(gcInfo.getGcName())
                        .append("\nHeap:\n Before: ");
                appendMemUsage(sb, beforeGc);
                sb.append("\n After: ");
                appendMemUsage(sb, afterGc);
                sb.append("\nDuration: ").append(gcInfo.getGcInfo().getDuration()).append(" ms");
                System.out.println(sb.toString());
                System.out.println("---Memory Region State---");
                printUsage(true);
                System.out.println();
            }
        }
    };

    public void printUsage(boolean heapOnly) {
        for(MemoryPoolMXBean mBean: ManagementFactory.getMemoryPoolMXBeans()) {
            if (!heapOnly || mBean.getType() == MemoryType.HEAP) {
                printMemUsage(mBean.getName(), mBean.getUsage());
            }
        }
    }

    public void startGCMonitor() {
        for(GarbageCollectorMXBean mBean: ManagementFactory.getGarbageCollectorMXBeans()) {
            ((NotificationEmitter) mBean).addNotificationListener(gcHandler, null, null);
        }
    }

    public void stopGCMonitor() {
        for(GarbageCollectorMXBean mBean: ManagementFactory.getGarbageCollectorMXBeans()) {
            try {
                ((NotificationEmitter) mBean).removeNotificationListener(gcHandler);
            } catch(ListenerNotFoundException e) {
            }
        }
    }

    private void printMemUsage(String title, MemoryUsage usage) {
        System.out.println(String.format("%-25s%10s\t%.1f%%\t[%s]",
                regions.get(title).getName(),
                formatMemory(usage.getUsed()),
                usage.getMax() < 0 ? 0.0 : (double)usage.getUsed() / (double)usage.getMax() * 100,
                formatMemory(usage.getMax())));
    }

    private String formatMemory(long bytes) {
        if (bytes > GIGABYTE) {
            return String.format("%.2fG", bytes / (double) GIGABYTE);
        } else if (bytes > MEGABYTE) {
            return String.format("%.2fM", bytes / (double) MEGABYTE);
        } else if (bytes > KILOBYTE) {
            return String.format("%.2fK", bytes / (double) KILOBYTE);
        }
        return Long.toString(bytes);
    }

    private void appendMemUsage(StringBuilder sb, Map<String, MemoryUsage> memUsage) {
        for(Map.Entry<String, MemoryUsage> entry: memUsage.entrySet()) {
            if (regions.get(entry.getKey()).isHeap()) {
                sb.append(entry.getKey()).append(" used=")
                        .append(formatMemory(entry.getValue().getUsed())).append("; ");
            }
        }
    }

    private void appendTime(StringBuilder sb, long millis) {
        sb.append(millis / MINUTE).append("min ")
        .append(millis % MINUTE / SECOND).append("s ")
        .append(millis % SECOND).append("ms");
    }
}