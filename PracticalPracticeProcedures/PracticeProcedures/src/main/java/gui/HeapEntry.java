package gui;

import model.values.IValue;

public class HeapEntry {
    private Integer address;
    private IValue value;

    public HeapEntry(Integer address, IValue value) {
        this.address = address;
        this.value = value;
    }

    public Integer getAddress() {
        return address;
    }

    public IValue getValue() {
        return value;
    }
}
