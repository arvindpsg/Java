package com.apple.internal.cache.impl;

import com.apple.internal.cache.Cache;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoftCacheImpl<K, V> implements Cache<K, V>, Serializable

{

    private static final long serialVersionUID = -1510534932773947339L;

    private volatile static Cache<Object, Object> objectCache = new SoftCacheImpl<Object, Object>();

    private Map<K, SoftValue<V>> cache;
    
    private ReferenceQueue<V> queue = new ReferenceQueue<V>();

    private SoftCacheImpl() {
        cache = new ConcurrentHashMap<K, SoftValue<V>>();
    }

    public static Cache<Object, Object> getInstance() {
        return objectCache;
    }


    private Object readResolve() throws ObjectStreamException {

        return objectCache;
    }


    @SuppressWarnings("unchecked")
    private void processQueue() {
        while (true) {
            Reference<? extends V> expiredValue = queue.poll();
            if (expiredValue == null) {
                return;
            }
            SoftValue<V> k = (SoftValue<V>) expiredValue;
            Object key = k.key;
            cache.remove(key);
        }
    }

    public V get(Object key) {
        processQueue();
        SoftReference<V> softValue = cache.get(key);
        if (softValue == null) {
            return null;
        }
        return softValue.get();
    }



    public void put(K key, V value) {
        processQueue();
        cache.put(key, new SoftValue<V>(value, queue, key));

    }

    private static class SoftValue<T> extends SoftReference<T> {
        final Object key;

        public SoftValue(T ref, ReferenceQueue<T> q, Object key) {
            super(ref, q);
            this.key = key;
        }

    }



}
