package model.adt.dictionary;

import model.adt.ADTException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ADTDictionary<K, V> implements IADTDictionary<K, V> {
    Map<K, V> dict;

    public ADTDictionary() {
        this.dict = new HashMap<K,V>();
    }

    public V lookup(K key) throws ADTException {
        if (!this.dict.containsKey(key)) {
            throw new ADTException("Invalid Key");
        }

        return this.dict.get(key);
    }

    public void put(K key, V val) {
        this.dict.put(key, val);
    }

    public boolean containsKey(K key) {
        return this.dict.containsKey(key);
    }

    public Set<K> keySet() { return this.dict.keySet(); }

    public void remove(K key) {
        this.dict.remove(key);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
//        if (this.dict.isEmpty())
//            return "\n";

        for (K key : this.dict.keySet()) {
            s.append(key).append(" -> ").append(this.dict.get(key)).append("\n");
        }

        return s.toString();

    }

}
