package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.prgstate.PrgState;
import model.types.IType;

public interface IStmt {
    PrgState execute(PrgState state) throws StmtException;
    IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException;
    String toString();
    IStmt deepCopy();
}
