package model.statements;

import model.prgstate.PrgState;

public class NOPStmt implements IStmt {

    public NOPStmt() {}

    public PrgState execute(PrgState state) throws StmtException {
        return null;
    }

    public IStmt deepCopy() {
        return new NOPStmt();
    }

    public String toString() {
        return "";
    }
}
