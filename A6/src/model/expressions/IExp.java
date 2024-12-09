package model.expressions;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.prgstate.IHeap;
import model.types.IType;
import model.values.IValue;

public interface IExp {
    IValue evaluate(IADTDictionary<String, IValue> table, IHeap heap) throws ExpException;
    IType typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException;
    String toString();
    IExp deepCopy();

}
