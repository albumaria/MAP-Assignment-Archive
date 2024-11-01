package model.prgstate;

import model.adt.dictionary.IADTDictionary;
import model.adt.list.IADTList;
import model.adt.stack.IADTStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;
import java.io.BufferedReader;

public class PrgState {
    private IADTStack<IStmt> exeStack;
    private IADTDictionary<String, IValue> symTable;
    private IADTList<IValue> output;
    private IADTDictionary<StringValue, BufferedReader> fileTable;
    IStmt originalProgram;

    public PrgState(IADTStack<IStmt> stack, IADTDictionary<String, IValue> stable, IADTList<IValue> out, IADTDictionary<StringValue, BufferedReader> ftable,IStmt prg) {
        this.exeStack = stack;
        this.symTable = stable;
        this.output = out;
        this.fileTable = ftable;
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

    public IADTDictionary<StringValue, BufferedReader> getFileTable() { return this.fileTable; }

    public void setExeStack(IADTStack<IStmt> st) {
        this.exeStack = st;
    }

    public void setSymTable(IADTDictionary<String, IValue> tbl) {
        this.symTable = tbl;
    }

    public void setOutput(IADTList<IValue> val) {
        this.output = val;
    }

    public void setFileTable(IADTDictionary<StringValue, BufferedReader> ftbl) { this.fileTable = ftbl; }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("ExeStack:\n");
        s.append(this.exeStack.toString());
        s.append("SymTable:\n");
        s.append(this.symTable.toString());
        s.append("Out:\n");
        s.append(this.output.toString());
        s.append("FileTable:\n");
        for (StringValue v : this.fileTable.keySet()) {
            s.append(v).append("\n");
        }

        return s.toString();

    }
}
