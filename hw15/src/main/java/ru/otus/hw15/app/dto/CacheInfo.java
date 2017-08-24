package ru.otus.hw15.app.dto;

/**
 * Created by Stanislav on 20.08.2017
 */
public class CacheInfo {

    private long cacheSize;

    private long cacheHit;

    private long cacheMiss;

    public CacheInfo() {
    }

    public CacheInfo(long cacheSize, long cacheHit, long cacheMiss) {
        this.cacheSize = cacheSize;
        this.cacheHit = cacheHit;
        this.cacheMiss = cacheMiss;
    }

    public long getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(long cacheSize) {
        this.cacheSize = cacheSize;
    }

    public long getCacheHit() {
        return cacheHit;
    }

    public void setCacheHit(long cacheHit) {
        this.cacheHit = cacheHit;
    }

    public long getCacheMiss() {
        return cacheMiss;
    }

    public void setCacheMiss(long cacheMiss) {
        this.cacheMiss = cacheMiss;
    }
}
