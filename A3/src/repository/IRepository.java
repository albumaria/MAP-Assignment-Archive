package repository;

import model.prgstate.PrgState;

public interface IRepository {
    PrgState getCurrentPrg();
    void logPrgStateExec() throws RepoException;
}
