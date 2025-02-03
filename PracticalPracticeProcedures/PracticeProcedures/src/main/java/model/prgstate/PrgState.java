package model.prgstate;

import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.adt.list.ADTList;
import model.adt.list.IADTList;
import model.adt.stack.ADTStack;
import model.adt.stack.IADTStack;
import model.statements.IStmt;
import model.values.IValue;

import java.util.concurrent.atomic.AtomicInteger;

public class PrgState {
    private static final AtomicInteger idInc = new AtomicInteger(0);
    private final int prgId;
    private IADTStack<IStmt> exeStack;
    private IADTStack<IADTDictionary<String, IValue>> symTableStack;
    private IADTList<IValue> output;
    private IFileTable fileTable;
    private IHeap heap;
    private IProcTable procTable;
    private IStmt originalProgram;

    public PrgState(IADTStack<IStmt> stack, IADTStack<IADTDictionary<String, IValue>> stable, IADTList<IValue> out, IFileTable ftable, IHeap heap, IProcTable procTable, IStmt prg) {
        this.prgId = idInc.getAndIncrement();
        this.exeStack = stack;
        this.symTableStack = stable;
        this.output = out;
        this.fileTable = ftable;
        this.heap = heap;
        this.procTable = procTable;
        this.originalProgram = prg.deepCopy();
    }

    public IADTStack<IStmt> getExeStack() {
        return this.exeStack;
    }

    public IADTDictionary<String, IValue> getSymTable() {
        return this.symTableStack.getAllList().getLast();
    }

    public IADTList<IValue> getOutput() {
        return this.output;
    }

    public IFileTable getFileTable() { return this.fileTable; }

    public IHeap getHeap() {
        return this.heap;
    }

    public IProcTable getProcTable() { return this.procTable; }

    public IStmt getOriginalProgram() {
        return this.originalProgram;
    }

    public IADTStack<IADTDictionary<String, IValue>> getSymTableStack() {
        return this.symTableStack;
    }

    public void setExeStack(IADTStack<IStmt> st) {
        this.exeStack = st;
    }

    public void setSymTable(IADTStack<IADTDictionary<String, IValue>> tbl) {
        this.symTableStack = tbl;
    }

    public void setOutput(IADTList<IValue> val) {
        this.output = val;
    }

    public void setFileTable(IFileTable ftbl) { this.fileTable = ftbl; }

    public void setHeap(IHeap heap) { this.heap = heap;}

    public int getPrgId() { return prgId; }

    public boolean isNotCompleted() {
        return !this.exeStack.isEmpty();
    }

    public PrgState oneStep() throws PrgStateException {
        if(this.exeStack.isEmpty())
            throw new PrgStateException("The execution stack is empty");

        IStmt currentStmt = this.exeStack.pop();
        return currentStmt.execute(this);
    }

    public String toString() {
        String s = "# " + this.prgId + "\n" +
                "ExeStack:\n" +
                this.exeStack.toString() +
                "SymTable:\n" +
                this.symTableStack.toString() +
                "Out:\n" +
                this.output.toString() +
                "FileTable:\n" +
                this.fileTable.toString() +
                "Heap:\n" +
                this.heap.toString() +
                "Proc Table:\n" +
                this.procTable.toString() +
                "\n";

        return s;

    }

    public void reinitializePrg() {
        this.exeStack = new ADTStack<IStmt>();
        this.symTableStack = new ADTStack<IADTDictionary<String, IValue>>();
        this.fileTable = new FileTable();
        this.output = new ADTList<IValue>();
        this.exeStack.push(this.originalProgram);
        this.originalProgram = this.originalProgram.deepCopy();

    }


}
