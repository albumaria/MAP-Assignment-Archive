package model.adt.stack;

import model.adt.ADTException;

import java.util.List;

public interface IADTStack<T> {
    T pop() throws ADTException;
    T peek() throws ADTException;
    void push(T element);
    boolean isEmpty();
    List<T> getAllList();
    String toString();

}
