package model.expressions;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.prgstate.IHeap;
import model.types.IType;
import model.values.IValue;

public class ValueExp implements IExp {
    private IValue value;

    public ValueExp(IValue v) {
        this.value = v;
    }

    public IValue evaluate(IADTDictionary<String, IValue> table, IHeap heap) throws ExpException {
        return this.value;
    }

    public IType typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        return this.value.getType();
    }

    public String toString() {
        return this.value.toString();
    }

    public IExp deepCopy() {
        return new ValueExp(this.value.deepCopy());
    }
}
