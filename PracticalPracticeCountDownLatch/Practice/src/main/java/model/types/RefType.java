package model.types;

import model.values.IValue;
import model.values.RefValue;

public class RefType implements IType {
    private IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    public IType getInner() {
        return this.inner;
    }

    public IValue defaultValue() {
        return new RefValue(0, this.inner);
    }

    public String toString() {
        return "Ref " + this.inner.toString();
    }

    public boolean equals(Object o) {
        if (o instanceof RefType){
            RefType another = (RefType) o;
            return this.inner.equals(another.getInner());
        }
        return false;
    }

}
