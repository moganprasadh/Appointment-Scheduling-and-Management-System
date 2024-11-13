package utility;

import java.util.HashMap;
import java.util.Map;

public class CacheUtil<K, V> {
    private final Map<K, V> cache;

    public CacheUtil() {
        cache = new HashMap<>();
    }

    public void add(K key, V value) {
        cache.put(key, value);
    }

    public V get(K key) {
        return cache.get(key);
    }

    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }
}
