package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.adt.list.IADTList;
import model.adt.stack.ADTStack;
import model.adt.stack.IADTStack;
import model.prgstate.*;
import model.types.BoolType;
import model.types.IType;
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
        ISemaphoreTable semTable = state.getSemaphoreTable();
        IFileTable fileTable = state.getFileTable();
        IADTList<IValue> output = state.getOutput();

        PrgState newPrg = new PrgState(exeStack, symTableCopy, output, fileTable, heap, semTable, this.threadStmt);

        return newPrg;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IADTDictionary<String, IType> typeEnvCopy = new ADTDictionary<String, IType>();
        Map<String, IType> typeEnvContent = typeEnv.getContent();
        for(Map.Entry<String, IType> entry : typeEnvContent.entrySet()) {
            IType aux = entry.getValue();
            typeEnvCopy.put(entry.getKey(), aux);
        }

        this.threadStmt.typeCheck(typeEnvCopy);

        return typeEnv;
    }

    public IStmt deepCopy() {
        return new forkStmt(this.threadStmt.deepCopy());
    }

    public String toString() {
        return "fork( " + this.threadStmt.toString() + " )";
    }
}
