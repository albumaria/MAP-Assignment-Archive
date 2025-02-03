package model.types;

import model.values.IValue;
import model.values.IntValue;

public class IntType implements IType{
    public IntType() {}

    public String toString() {
        return "int";
    }

    public IValue defaultValue() {
        return new IntValue(0);
    }

    public boolean equals(Object o) {
        return o instanceof IntType;
    }
}
