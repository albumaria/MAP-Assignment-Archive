package gui;

import javafx.util.Pair;
import model.values.IValue;

import java.util.ArrayList;

public class SemTableEntry {
    private Integer address;
    private Pair<Integer, ArrayList<Integer>> value;

    public SemTableEntry(Integer address, Pair<Integer, ArrayList<Integer>> value) {
        this.address = address;
        this.value = value;
    }

    public Integer getAddress() {
        return address;
    }

    public Pair<Integer, ArrayList<Integer>> getValue() {
        return value;
    }
}
