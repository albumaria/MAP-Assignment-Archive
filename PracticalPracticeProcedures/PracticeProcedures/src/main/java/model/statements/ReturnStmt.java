package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.adt.stack.IADTStack;
import model.prgstate.PrgState;
import model.types.IType;
import model.values.IValue;

public class ReturnStmt implements IStmt {
    public ReturnStmt() {
        return;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTStack<IADTDictionary<String, IValue>> symTableStack = state.getSymTableStack();
        symTableStack.pop();

        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        return typeEnv;
    }

    public IStmt deepCopy() {
        return new ReturnStmt();
    }

    public String toString() {
        return "return";
    }
}
