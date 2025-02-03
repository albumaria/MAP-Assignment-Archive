package gui;

import model.values.IValue;

public class SymTableEntry {
    private String name;
    private IValue value;

    public SymTableEntry(String name, IValue value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public IValue getValue() {
        return value;
    }
}
