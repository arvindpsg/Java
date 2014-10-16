package com.apple.internal.cache.impl;

import com.apple.internal.cache.Cache;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LRUCacheImpl<K, V> implements Cache<K, V>, Serializable {
    
    private static final long serialVersionUID = -1510534932773947339L;

    private volatile static Cache<Object, Object> objectCache = new LRUCacheImpl<Object, Object>(25);


    // Max capacity for the cache
    private final int capacity;

    // Queue to track the recently used keys.
    private ConcurrentLinkedQueue<K> queue;

   //thread safe data structure for cache
    private Map<K, V> cache;
    
    
    public static Cache<Object, Object> getObjectCache() {
        return objectCache;
    }
    
    private Object readResolve() throws ObjectStreamException {

        return objectCache;
    }
        


    public LRUCacheImpl(final int capacity) {
        this.capacity = capacity;
        this.queue = new ConcurrentLinkedQueue<K>();
        this.cache = new ConcurrentHashMap<K, V>(capacity);
    }


    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }

        synchronized (this) {
            if (cache.containsKey(key)) {
                queue.remove(key);
            }
            while (queue.size() >= capacity) {
                K expiredKey = queue.poll();
                if (expiredKey != null) {
                    cache.remove(expiredKey);
                }
            }
            queue.add(key);
            cache.put(key, value);
        }

    }

    public V get(K key) {
        return cache.get(key);
    }

}
