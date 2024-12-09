package repository;

import model.prgstate.PrgState;
import model.statements.IStmt;

import java.util.List;

public interface IRepository {
    void logPrgStateExec(PrgState state) throws RepoException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> list);
    void restartRepo(IStmt originalProgram);
    IStmt getOriginalPrg();
}
