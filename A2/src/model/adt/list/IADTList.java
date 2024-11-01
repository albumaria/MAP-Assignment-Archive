package model.adt.list;

import model.adt.ADTException;

public interface IADTList<T> {
    void add(T element);
    void remove(T element) throws ADTException;
    T get(int index) throws ADTException;
    int size();
    String toString();

}
