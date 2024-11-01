package model.adt.list;

import model.adt.ADTException;

import java.util.ArrayList;
import java.util.List;

public class ADTList<T> implements IADTList<T> {
    List<T> list;

    public ADTList() {
        this.list = new ArrayList<>();
    }

    public void add(T element) {
        this.list.add(element);
    }

    public void remove(T element) throws ADTException {
        if (!this.list.contains(element)) {
            throw new ADTException("Element to delete not found in list");
        }

        this.list.remove(element);
    }

    public T get(int index) throws ADTException {
        if (index >= this.list.size()) {
            throw new ADTException("List index out of range");
        }
        return this.list.get(index);

    }

    public int size() {
        return this.list.size();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
//        if (this.list.isEmpty())
//            return "\n";

        for (T elem : this.list) {
            s.append(elem).append('\n');
        }

        return s.toString();
    }
}
