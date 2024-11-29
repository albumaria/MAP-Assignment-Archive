package model.expressions;

import model.adt.dictionary.IADTDictionary;
import model.prgstate.IHeap;
import model.values.IValue;

public interface IExp {
    IValue evaluate(IADTDictionary<String, IValue> table, IHeap heap) throws ExpException;
    String toString();
    IExp deepCopy();
}
