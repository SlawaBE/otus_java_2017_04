package ru.otus.hw15.db;

import ru.otus.hw15.app.CacheEngine;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

/**
 * Created by Stanislav on 21.06.2017
 */
public class MyCacheImpl<K, V> implements CacheEngine<K, V> {

    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, Element<V>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private long hit = 0;
    private long miss = 0;

    public MyCacheImpl(int maxElements, long lifeTimeMs, long idleTimeMs) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = idleTimeMs == 0 && lifeTimeMs == 0;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void put(K key, V value) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        elements.put(key, new Element<>(value));

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs);
            }
        }
    }

    @Override
    public V get(K key) {
        Element<V> element = elements.get(key);
        if (element != null) {
            V value = element.getValue();
            if (value != null) {
                element.updateAccessTime();
                hit++;
                return value;
            } else {
                elements.remove(key);
            }
        }
        miss++;
        return null;
    }

    private TimerTask getTimerTask(final K key, Function<Element<V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                Element<V> checkedElement = elements.get(key);
                if (checkedElement == null ||
                        isT1BeforeT2(timeFunction.apply(checkedElement), System.currentTimeMillis())) {
                    elements.remove(key);
                }
            }
        };
    }

    @Override
    public long getHit() {
        return hit;
    }

    @Override
    public long getMiss() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

    private class Element<V> {
        private final SoftReference<V> value;
        private final long creationTime;
        private long lastAccessTime;

        Element(V value) {
            this.value = new SoftReference<>(value);
            this.creationTime = getCurrentTime();
            this.lastAccessTime = creationTime;
        }

        private long getCurrentTime() {
            return System.currentTimeMillis();
        }

        V getValue() {
            return value.get();
        }

        long getCreationTime() {
            return creationTime;
        }

        long getLastAccessTime() {
            return lastAccessTime;
        }

        void updateAccessTime() {
            lastAccessTime = getCurrentTime();
        }
    }

}
