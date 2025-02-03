package model.prgstate;

import javafx.util.Pair;
import model.values.IValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SemaphoreTable implements ISemaphoreTable {
    private Map<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable;
    private int locationCounter;

    public SemaphoreTable() {
        this.semaphoreTable = new ConcurrentHashMap<>();
        this.locationCounter = 1;
    }

    public Integer getLastKey() {
        Set<Integer> keySet = this.semaphoreTable.keySet();
        List<Integer> keyList = new ArrayList<>(keySet);
        return keyList.getLast();
    }

    private void nextAddress() {
        this.locationCounter = this.getLastKey() + 1;
    }

    public boolean containsAddress(Integer address) {
        return this.semaphoreTable.containsKey(address);
    }

    public Pair<Integer, ArrayList<Integer>> returnPair(Integer address) {
        return this.semaphoreTable.get(address);
    }

    public void removeFromSemTable(Integer address) {
        this.semaphoreTable.remove(address);
    }

    public void updateSemTable(Integer address, Pair<Integer, ArrayList<Integer>> value) {
        this.semaphoreTable.put(address, value);
    }

    public Integer getLastAddress() {
        return this.locationCounter - 1;
    }

    public Map<Integer, Pair<Integer, ArrayList<Integer>>> getContent() {
        return this.semaphoreTable;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        for (Integer key : this.semaphoreTable.keySet()) {
            s.append(key).append(" -> ").append(this.semaphoreTable.get(key)).append("\n");
        }

        return s.toString();
    }

    public void addToSemTable(Pair<Integer, ArrayList<Integer>> semEntry) {
        this.semaphoreTable.put(this.locationCounter, semEntry);
    }
}
