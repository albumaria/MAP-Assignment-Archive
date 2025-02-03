package model.prgstate;

import model.values.IValue;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Heap implements IHeap {
    private Map<Integer, IValue> heap;
    private int addressCounter;

    public Heap() {
        this.heap = new ConcurrentHashMap<Integer, IValue>();
        this.addressCounter = 1;
    }

    public Integer getLastKey() {
        Set<Integer> keySet = this.heap.keySet();
        List<Integer> keyList = new ArrayList<>(keySet);
        return keyList.getLast();
    }

    private void nextAddress() {
        this.addressCounter = this.getLastKey() + 1;
    }

    public boolean containsAddress(Integer address) {
        return this.heap.containsKey(address);
    }

    public IValue returnValue(Integer address) {
        return this.heap.get(address);
    }

    public void addToHeap(IValue value) {
        this.heap.put(this.addressCounter, value);
        this.nextAddress();
    }

    public void removeFromHeap(Integer address) {
        this.heap.remove(address);
    }

    public void updateHeap(Integer address, IValue value) {
        this.heap.put(address, value);
    }

    public Integer getLastAddress() {
        return this.addressCounter - 1;
    }

    public Map<Integer, IValue> getContent() {
        return this.heap;
    }

    public void setContent(Map<Integer, IValue> newContent) {
        this.heap = newContent;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        for (Integer key : this.heap.keySet()) {
            s.append(key).append(" -> ").append(this.heap.get(key)).append("\n");
        }

        return s.toString();
    }
}
