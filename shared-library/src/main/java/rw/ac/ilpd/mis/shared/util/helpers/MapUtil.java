package rw.ac.ilpd.mis.shared.util.helpers;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 17/07/2025
 */

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    // Add a key-value pair to a map
    public static <K, V> void put(Map<K, V> map, K key, V value) {
        map.put(key, value);
    }

    // Get a value by key
    public static <K, V> V get(Map<K, V> map, K key) {
        return map.get(key);
    }

    // Check if a key exists
    public static <K, V> boolean containsKey(Map<K, V> map, K key) {
        return map.containsKey(key);
    }

    // Remove a key
    public static <K, V> void remove(Map<K, V> map, K key) {
        map.remove(key);
    }

    // Print all entries
    public static <K, V> void printMap(Map<K, V> map) {
        map.forEach((k, v) -> System.out.println(k + " -> " + v));
    }

    // Create a new empty map
    public static <K, V> Map<K, V> newMap() {
        return new HashMap<>();
    }
}

