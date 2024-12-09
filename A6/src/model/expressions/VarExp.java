package model.expressions;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.prgstate.IHeap;
import model.types.IType;
import model.values.IValue;

public class VarExp implements IExp {
    String name;

    public VarExp(String n) {
        this.name = n;
    }

    public IValue evaluate(IADTDictionary<String, IValue> table, IHeap heap) throws ExpException {
        if(!table.containsKey(this.name))
            throw new ExpException("Variable " + this.name + " not declared, cannot evaluate expression");

        return table.lookup(this.name);
    }

    public IType typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        return typeEnv.lookup(this.name);
    }

    public IExp deepCopy() {
        return new VarExp(new String(this.name));
    }

    public String toString() {
        return this.name;
    }
}
