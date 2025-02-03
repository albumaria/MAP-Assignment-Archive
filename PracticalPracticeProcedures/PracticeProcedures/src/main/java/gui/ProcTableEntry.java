package gui;

import model.adt.list.IADTList;
import model.statements.IStmt;
import model.values.IValue;

import java.util.Map;

public class ProcTableEntry {
    private String name;
    private Map<IADTList<String>, IStmt> tuple;

    public ProcTableEntry(String name, Map<IADTList<String>, IStmt> value) {
        this.name = name;
        this.tuple = value;
    }

    public String getName() {
        return this.name;
    }

    public Map<IADTList<String>, IStmt> getValue() {
        return this.tuple;
    }
}
