package model.prgstate;

import model.values.IValue;
import model.values.IntValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LatchTable implements ILatchTable {
    private Map<Integer, IntValue> latchTable;
    private int locationCounter;

    public LatchTable() {
        this.latchTable = new ConcurrentHashMap<Integer, IntValue>();
        this.locationCounter = 1;
    }

    public Integer getLastKey() {
        Set<Integer> keySet = this.latchTable.keySet();
        List<Integer> keyList = new ArrayList<>(keySet);
        return keyList.getLast();
    }

    public Integer getLatchValue(Integer index) {
        return this.latchTable.get(index).getValue();
    }

    public boolean containsKey(Integer index) {
        return this.latchTable.containsKey(index);
    }

    public void updateLatchEntry(Integer index, IntValue value) {
        this.latchTable.put(index, value);
    }

    private void nextAddress() {
        this.locationCounter = this.getLastKey() + 1;
    }

    public void addToLatchTable(IntValue value) {
        this.latchTable.put(this.locationCounter, value);
        this.nextAddress();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        for (Integer key : this.latchTable.keySet()) {
            s.append(key).append(" -> ").append(this.latchTable.get(key)).append("\n");
        }

        return s.toString();
    }

    public Map<Integer, IntValue> getContent() {
        return this.latchTable;
    }
}
