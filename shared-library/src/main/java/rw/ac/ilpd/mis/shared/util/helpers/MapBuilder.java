package rw.ac.ilpd.mis.shared.util.helpers;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 15/07/2025
 */
import java.util.Map;

public class MapBuilder {

    // Accept varargs as alternating key-value pairs and return a HashMap
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> buildMap(Object... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Must provide an even number of arguments (key-value pairs).");
        }

        Map<K, V> map = MapUtil.newMap();

        for (int i = 0; i < keyValuePairs.length; i += 2) {
            K key = (K) keyValuePairs[i];
            V value = (V) keyValuePairs[i + 1];
            MapUtil.put(map, key, value);
        }

        return map;
    }
}

