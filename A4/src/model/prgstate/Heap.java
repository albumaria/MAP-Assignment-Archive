package model.prgstate;

import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.values.IValue;

import java.util.Map;

public class Heap implements IHeap {
    private IADTDictionary<Integer, IValue> heap;
    private int addressCounter;

    public Heap() {
        this.heap = new ADTDictionary<Integer, IValue>();
        this.addressCounter = 1;
    }

    private void nextAddress() {
        this.addressCounter = this.heap.getLastKey() + 1;
    }

    public boolean containsAddress(Integer address) {
        return this.heap.containsKey(address);
    }

    public IValue returnValue(Integer address) {
        return this.heap.lookup(address);
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
        return this.heap.getContent();
    }

    public void setContent(Map<Integer, IValue> newContent) {
        this.heap.setContent(newContent);
    }

    public String toString() {
        return this.heap.toString();
    }
}
