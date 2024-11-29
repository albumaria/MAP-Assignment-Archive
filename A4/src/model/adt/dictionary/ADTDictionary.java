package model.adt.dictionary;

import model.adt.ADTException;

import javax.lang.model.type.ArrayType;
import java.util.*;

public class ADTDictionary<K, V> implements IADTDictionary<K, V> {
    private Map<K, V> dict;

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

    public Set<Map.Entry<K, V>> entrySet() {
        return this.dict.entrySet();
    }

    public Map<K, V> getContent() {
        return this.dict;
    }

    public void setContent(Map<K, V> map) {
        this.dict = map;
    }

    public K getLastKey() {
        Set<K> keySet = this.dict.keySet();
        List<K> keyList = new ArrayList<>(keySet);
        return keyList.getLast();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        for (K key : this.dict.keySet()) {
            s.append(key).append(" -> ").append(this.dict.get(key)).append("\n");
        }

        return s.toString();

    }

}
