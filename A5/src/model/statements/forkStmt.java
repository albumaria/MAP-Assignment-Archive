package model.statements;

import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.adt.list.IADTList;
import model.adt.stack.ADTStack;
import model.adt.stack.IADTStack;
import model.prgstate.Heap;
import model.prgstate.IFileTable;
import model.prgstate.IHeap;
import model.prgstate.PrgState;
import model.values.IValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class forkStmt implements IStmt {
    private IStmt threadStmt;

    public forkStmt(IStmt instructions) {
        this.threadStmt = instructions;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> symTable = state.getSymTable();

        IADTStack<IStmt> exeStack = new ADTStack<IStmt>();
        exeStack.push(this.threadStmt);

        IADTDictionary<String, IValue> symTableCopy = new ADTDictionary<String, IValue>();
        Map<String, IValue> originalSymContent = symTable.getContent();
        for(Map.Entry<String, IValue> entry : originalSymContent.entrySet()) {
            IValue aux = entry.getValue().deepCopy();
            symTableCopy.put(entry.getKey(), entry.getValue().deepCopy());
        }

        IHeap heap = state.getHeap();
        IFileTable fileTable = state.getFileTable();
        IADTList<IValue> output = state.getOutput();

        PrgState newPrg = new PrgState(exeStack, symTableCopy, output, fileTable, heap, this.threadStmt);

        return newPrg;
    }

    public IStmt deepCopy() {
        return new forkStmt(this.threadStmt.deepCopy());
    }

    public String toString() {
        return "fork( " + this.threadStmt.toString() + " )";
    }
}
