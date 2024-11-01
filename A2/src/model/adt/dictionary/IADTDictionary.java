package model.adt.dictionary;

import model.adt.ADTException;

public interface IADTDictionary<K, V> {
    V lookup(K key) throws ADTException;
    void put(K key, V val);
    boolean containsKey(K key);
    String toString();

}
