package ru.otus.hw13.cache;

/**
 * Created by Stanislav on 03.08.2017
 */
public class StateCacheDecorator implements CacheEngine {

    private CacheEngine cache;

    public StateCacheDecorator(CacheEngine cache) {
        this.cache = cache;
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public void put(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object get(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getHit() {
        return cache.getHit();
    }

    @Override
    public long getMiss() {
        return cache.getMiss();
    }
}
