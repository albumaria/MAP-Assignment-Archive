package model.values;

import model.types.IType;
import model.types.IntType;

public class IntValue implements IValue{
    int value;

    public IntValue() {
        this.value = 0;
    }

    public IntValue(int v) {
        this.value = v;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int v) {
        this.value = v;
    }

    public boolean equals(Object o) {
        if (!(o instanceof IValue)) {
            return false;
        }
        else {
            IValue v = (IValue) o;
            if (v.getType() != this.getType())
                return false;

            return ((IntValue)v).getValue() == this.value;
        }
    }

    public String toString() {
        return "" + this.value;
    }

    public IType getType() {
        return new IntType();
    }

    public IValue deepCopy() {
        return new IntValue(this.value);
    }
}
