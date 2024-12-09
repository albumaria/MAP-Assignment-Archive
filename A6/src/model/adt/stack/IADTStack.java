package model.adt.stack;

import model.adt.ADTException;

public interface IADTStack<T> {
    T pop() throws ADTException;
    T peek() throws ADTException;
    void push(T element);
    boolean isEmpty();
    String toString();

}
