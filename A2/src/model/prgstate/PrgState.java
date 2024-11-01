package model.prgstate;

import model.adt.dictionary.IADTDictionary;
import model.adt.list.IADTList;
import model.adt.stack.IADTStack;
import model.statements.IStmt;
import model.values.IValue;

public class PrgState {
    private IADTStack<IStmt> exeStack;
    private IADTDictionary<String, IValue> symTable;
    private IADTList<IValue> output;
    IStmt originalProgram;

    public PrgState(IADTStack<IStmt> stack, IADTDictionary<String, IValue> dict, IADTList<IValue> list, IStmt prg) {
        this.exeStack = stack;
        this.symTable = dict;
        this.output = list;
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

    public void setExeStack(IADTStack<IStmt> st) {
        this.exeStack = st;
    }

    public void setSymTable(IADTDictionary<String, IValue> tbl) {
        this.symTable = tbl;
    }

    public void setOutput(IADTList<IValue> val) {
        this.output = val;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("ExeStack:\n");
        s.append(this.exeStack.toString());
        s.append("SymTable:\n");
        s.append(this.symTable.toString());
        s.append("Out:\n");
        s.append(this.output.toString());

        return s.toString();

    }
}
