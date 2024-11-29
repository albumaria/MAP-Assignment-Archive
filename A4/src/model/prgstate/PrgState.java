package model.prgstate;

import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.adt.list.ADTList;
import model.adt.list.IADTList;
import model.adt.stack.ADTStack;
import model.adt.stack.IADTStack;
import model.statements.IStmt;
import model.values.IValue;

public class PrgState {
    private IADTStack<IStmt> exeStack;
    private IADTDictionary<String, IValue> symTable;
    private IADTList<IValue> output;
    private IFileTable fileTable;
    private IHeap heap;
    IStmt originalProgram;

    public PrgState(IADTStack<IStmt> stack, IADTDictionary<String, IValue> stable, IADTList<IValue> out, IFileTable ftable, IHeap heap, IStmt prg) {
        this.exeStack = stack;
        this.symTable = stable;
        this.output = out;
        this.fileTable = ftable;
        this.heap = heap;
        this.originalProgram = prg.deepCopy();
    }

    public IADTStack<IStmt> getExeStack() {
        return this.exeStack;
    }

    public IADTDictionary<String, IValue> getSymTable() {
        return this.symTable;
    }

    public IADTList<IValue> getOutput() {
        return this.output;
    }

    public IFileTable getFileTable() { return this.fileTable; }

    public IHeap getHeap() {
        return this.heap;
    }

    public void setExeStack(IADTStack<IStmt> st) {
        this.exeStack = st;
    }

    public void setSymTable(IADTDictionary<String, IValue> tbl) {
        this.symTable = tbl;
    }

    public void setOutput(IADTList<IValue> val) {
        this.output = val;
    }

    public void setFileTable(IFileTable ftbl) { this.fileTable = ftbl; }

    public void setHeap(IHeap heap) { this.heap = heap;}

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("ExeStack:\n");
        s.append(this.exeStack.toString());
        s.append("SymTable:\n");
        s.append(this.symTable.toString());
        s.append("Out:\n");
        s.append(this.output.toString());
        s.append("FileTable:\n");
        s.append(this.fileTable.toString());
        s.append("Heap:\n");
        s.append(this.heap.toString());

        return s.toString();

    }

    public void reinitializePrg() {
        this.exeStack = new ADTStack<IStmt>();
        this.symTable = new ADTDictionary<String, IValue>();
        this.fileTable = new FileTable();
        this.output = new ADTList<IValue>();
        this.exeStack.push(this.originalProgram);
        this.originalProgram = this.originalProgram.deepCopy();

    }
}
