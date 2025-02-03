package model.adt.list;

import model.adt.ADTException;

import java.util.List;

public interface IADTList<T> {
    void add(T element);
    void remove(T element) throws ADTException;
    T get(int index) throws ADTException;
    int size();
    List<T> getContent();
    boolean contains(T element);
    String toString();

}
