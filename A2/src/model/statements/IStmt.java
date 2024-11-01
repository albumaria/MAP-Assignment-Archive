package model.statements;

import model.prgstate.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws StmtException;
    String toString();
    IStmt deepCopy();
}
