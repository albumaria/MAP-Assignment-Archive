package model.types;

import model.values.BoolValue;
import model.values.IValue;

public class BoolType implements IType {
    public BoolType() {}

    public String toString() {
        return "bool";
    }

    public IValue defaultValue() {
        return new BoolValue(false);
    }

    public boolean equals(Object o) {
        return o instanceof BoolType;
    }

}
