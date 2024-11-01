package model.expressions;

import com.sun.jdi.Value;
import model.adt.dictionary.IADTDictionary;
import model.values.IValue;

public class ValueExp implements IExp {
    private IValue value;

    public ValueExp(IValue v) {
        this.value = v;
    }

    public IValue evaluate(IADTDictionary<String, IValue> table) throws ExpException {
        return this.value;
    }

    public String toString() {
        return this.value.toString();
    }

    public IExp deepCopy() {
        return new ValueExp(this.value.deepCopy()); // not sure here?
    }
}
