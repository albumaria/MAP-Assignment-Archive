package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue {
    private String value;

    public StringValue() {
        this.value = "";
    }

    public StringValue(String s) {
        this.value = s;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String s) {
        this.value = s;
    }

    public boolean equals(Object o) {
        if (!(o instanceof IValue)) {
            return false;
        }
        else {
            IValue v = (IValue) o;
            if (v.getType() != this.getType())
                return false;

            return ((StringValue)v).getValue().equals(this.value);
        }
    }

    public IType getType() {
        return new StringType();
    }

    public String toString() {
        return this.value;
    }

    public IValue deepCopy() {
        return new StringValue(this.value);
    }
}
