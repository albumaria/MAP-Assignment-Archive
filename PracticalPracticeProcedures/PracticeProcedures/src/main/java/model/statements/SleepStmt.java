package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.adt.stack.IADTStack;
import model.prgstate.PrgState;
import model.types.IType;

public class SleepStmt implements IStmt {
    private Integer number;

    public SleepStmt(Integer nr) {
        this.number = nr;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTStack<IStmt> exeStack = state.getExeStack();

        if (number > 0) {
            exeStack.push(new SleepStmt(number - 1));
        }

        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        return typeEnv;
    }

    public IStmt deepCopy() {
        return new SleepStmt(this.number);
    }

    public String toString() {
        return "sleep(" + this.number + ")";
    }
}
