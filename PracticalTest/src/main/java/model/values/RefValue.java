package model.values;

import model.types.IType;
import model.types.RefType;

public class RefValue implements IValue {
    private int address;
    private IType locationType;

    public RefValue(int adr, IType type) {
        this.address = adr;
        this.locationType = type;
    }

    public int getAddress() {
        return this.address;
    }

    public IType getType() {
        return new RefType(this.locationType);
    }

    public IValue deepCopy() {
        return new RefValue(this.address, this.locationType);
    }

    public String toString() {
        return "(" + this.address + ", " + this.locationType.toString() +")";
    }

    public boolean equals(Object o) {
        if (o instanceof RefValue){
            RefValue another = (RefValue) o;
            return this.address == another.getAddress() && this.locationType.equals(another.getType());
        }
        return false;
    }
}
