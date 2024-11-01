package model.values;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue{
    boolean value;

    public BoolValue() {
        this.value = false;
    }

    public BoolValue(boolean v) {
        this.value = v;
    }

    public boolean getValue() {
        return this.value;
    }

    public void setValue(boolean v) {
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

            return ((BoolValue)v).getValue() == this.value;
        }
    }

    public String toString() {
        return "" + this.value;
    }

    public IType getType() {
        return new BoolType();
    }

    public IValue deepCopy() {
        return new BoolValue(this.value);
    }
}
