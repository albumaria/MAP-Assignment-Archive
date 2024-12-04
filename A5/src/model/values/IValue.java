package model.values;

import model.types.IType;

public interface IValue {
    String toString();
    boolean equals(Object o);
    IType getType();
    IValue deepCopy();
}
