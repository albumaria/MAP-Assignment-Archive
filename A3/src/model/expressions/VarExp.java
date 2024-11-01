package model.expressions;

import model.adt.dictionary.IADTDictionary;
import model.values.IValue;

public class VarExp implements IExp {
    String name;

    public VarExp(String n) {
        this.name = n;
    }

    public IValue evaluate(IADTDictionary<String, IValue> table) throws ExpException {
        if(!table.containsKey(this.name))
            throw new ExpException("Variable " + this.name + " not declared, cannot evaluate expression");

        return table.lookup(this.name);
    }

    public IExp deepCopy() {
        return new VarExp(new String(this.name));
    }

    public String toString() {
        return this.name;
    }
}
