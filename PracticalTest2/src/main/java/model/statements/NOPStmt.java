package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.prgstate.PrgState;
import model.types.IType;

public class NOPStmt implements IStmt {

    public NOPStmt() {}

    public PrgState execute(PrgState state) throws StmtException {
        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        return typeEnv;
    }

    public IStmt deepCopy() {
        return new NOPStmt();
    }

    public String toString() {
        return "";
    }
}
