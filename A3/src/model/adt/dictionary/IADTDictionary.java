package model.adt.dictionary;

import model.adt.ADTException;
import view.commands.Command;

import java.util.Set;

public interface IADTDictionary<K, V> {
    V lookup(K key) throws ADTException;
    void put(K key, V val);
    boolean containsKey(K key);
    Set<K> keySet();
    void remove(K key);
    String toString();

}
