package gui;

import model.values.IValue;
import model.values.IntValue;

public class LatchTableEntry {
    private Integer address;
    private IntValue value;

    public LatchTableEntry(Integer address, IntValue value) {
        this.address = address;
        this.value = value;
    }

    public Integer getIndex() {
        return address;
    }

    public IntValue getValue() {
        return value;
    }
}
