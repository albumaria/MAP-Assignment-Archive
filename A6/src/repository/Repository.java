package repository;

import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.adt.list.ADTList;
import model.adt.list.IADTList;
import model.adt.stack.ADTStack;
import model.adt.stack.IADTStack;
import model.prgstate.*;
import model.statements.IStmt;
import model.values.IValue;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> prgList;
    private String logFilePath;

    public Repository(PrgState program, String fileName) {
        this.prgList = new ArrayList<PrgState>();
        this.logFilePath = fileName;
        this.prgList.add(program);
    }

    public void logPrgStateExec(PrgState state) throws RepoException {
        PrintWriter logFile = null;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(state);
        }
        catch (IOException e) {
            throw new RepoException("IO Exception occurred for the repository log file");
        }
        finally {
            if (logFile != null)
                logFile.close();
        }

    }

    public List<PrgState> getPrgList() {
        return this.prgList;
    }

    public void setPrgList(List<PrgState> list) {
        this.prgList = list;
    }

    public void add(PrgState state) {
        this.prgList.add(state);
    }

    public IStmt getOriginalPrg() {
        return this.prgList.getFirst().getOriginalProgram();
    }

    public void restartRepo(IStmt originalProgram) {
        this.prgList.clear();
        IADTStack<IStmt> exeStack = new ADTStack<IStmt>();
        exeStack.push(originalProgram);
        IADTDictionary<String, IValue> symTable = new ADTDictionary<String, IValue>();
        IADTList<IValue> output = new ADTList<IValue>();
        IFileTable fileTable = new FileTable();
        IHeap heap = new Heap();
        IStmt originalPrgCopy = originalProgram.deepCopy();

        PrgState initialPrg = new PrgState(exeStack, symTable, output, fileTable, heap, originalPrgCopy);
    }
}