package repository;

import model.prgstate.PrgState;
import model.statements.IStmt;

import java.util.List;
import java.util.Set;

public interface IRepository {
    void logPrgStateExec(PrgState state) throws RepoException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> list);
    PrgState getPrgById(int id);
    Set<Integer> getPrgIdSet();
    //void restartRepo(IStmt originalProgram);
    IStmt getOriginalPrg();
}
