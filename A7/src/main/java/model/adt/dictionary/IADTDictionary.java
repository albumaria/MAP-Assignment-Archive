package model.adt.dictionary;

import model.adt.ADTException;

import java.util.Map;
import java.util.Set;

public interface IADTDictionary<K, V> {
    V lookup(K key) throws ADTException;
    void put(K key, V val);
    boolean containsKey(K key);
    Set<K> keySet();
    void remove(K key);
    Set<Map.Entry<K, V>> entrySet();
    Map<K, V> getContent();
    void setContent(Map<K, V> map);
    K getLastKey();
    String toString();


}
