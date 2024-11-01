package repository;

import model.adt.list.ADTList;
import model.adt.list.IADTList;
import model.prgstate.PrgState;

import java.io.*;

public class Repository implements IRepository {
    IADTList<PrgState> prgList;
    String logFilePath;

    public Repository(PrgState program, String fileName) {
        this.prgList = new ADTList<PrgState>();
        this.logFilePath = fileName;
        this.prgList.add(program);
    }

    public PrgState getCurrentPrg() {
        return prgList.get(0);
    }

    public void logPrgStateExec() throws RepoException {
        PrintWriter logFile = null;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(this.getCurrentPrg());
        }
        catch (IOException e) {
            throw new RepoException("IO Exception occurred for the repository log file");
        }
        finally {
            if (logFile != null)
                logFile.close();
        }

    }

    public void add(PrgState state) {
        this.prgList.add(state);
    }
}
