package model.statements;

import model.adt.stack.IADTStack;
import model.prgstate.PrgState;


public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    public String toString() {
        return first.toString() + " ; " + second.toString() + " ";
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTStack<IStmt> stack = state.getExeStack();
        stack.push(this.second);
        stack.push(this.first);

        return state;
    }

    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }
}
