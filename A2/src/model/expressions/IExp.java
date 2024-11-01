package model.expressions;

import model.adt.dictionary.IADTDictionary;
import model.values.IValue;

public interface IExp {
    IValue evaluate(IADTDictionary<String, IValue> table) throws ExpException;
    String toString();
    IExp deepCopy();
}
