package ru.otus.hw13.cache;

/**
 * Created by Stanislav on 23.06.2017
 */
public interface CacheEngine<K, V> {

    int size();

    void put(K key, V value);

    V get(K key);

    void dispose();

    long getHit();

    long getMiss();

}
