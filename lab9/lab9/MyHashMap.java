package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        return hash(key, buckets);
    }

    private int hash(K key, ArrayMap<K, V>[] list) {
        if (key == null) {
            return 0;
        }
        int mod = list.length;
        return Math.floorMod(key.hashCode(), mod);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int index = hash(key);
        return buckets[index].get(key);
        //throw new UnsupportedOperationException();
    }

    public boolean containsKey(K target) {
        int index = hash(target);
        return buckets[index].containsKey(target);
    }

    private void resize(int cap) {
        Set<K> allKeys = keySet();

        ArrayMap[] temp = new ArrayMap[cap];
        for(int i = 0; i < temp.length; i++) {
            temp[i] = new ArrayMap();
        }

        for(K i : allKeys) {
            int newIndex = hash(i,temp);
            V value = get(i);
            if(value != null) {
                temp[newIndex].put(i, value);
            }
        }

        buckets = temp;

    }
    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        //throw new UnsupportedOperationException();
        if(!(containsKey(key))) {
            size += 1;
            if (loadFactor() > MAX_LF) {
                resize(2 * buckets.length);
            }
        }
            int index = hash(key);
            buckets[index].put(key, value);

    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        //throw new UnsupportedOperationException();
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        //throw new UnsupportedOperationException();
        Set<K> ans = new HashSet<>();
        for (ArrayMap<K, V> bucket : buckets) {
            Set<K> keys = bucket.keySet();
            for (K some : keys) {
                ans.add(some);
            }
        }
        return ans;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        //throw new UnsupportedOperationException();
        if(containsKey(key)) {
            int index = hash(key);
            return buckets[index].remove(key);
        }
        return null;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        //throw new UnsupportedOperationException();
        if(containsKey(key)) {
            int index = hash(key);
            V test = get(key);
            if(test.equals(value)) {
                return buckets[index].remove(key);
            }
        }
        return null;
    }


    @Override
    public Iterator<K> iterator() {
        //throw new UnsupportedOperationException();
        return keySet().iterator();
    }
}
