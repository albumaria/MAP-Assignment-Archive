package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType {

    public String toString() {
        return "string";
    }

    public IValue defaultValue() {
        return new StringValue("");
    }

    public boolean equals(Object o) {
        return o instanceof IntType;
    }
}
