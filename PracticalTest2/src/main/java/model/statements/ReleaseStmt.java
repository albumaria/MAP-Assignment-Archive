package model.statements;

import javafx.util.Pair;
import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.adt.stack.IADTStack;
import model.prgstate.ISemaphoreTable;
import model.prgstate.PrgState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.ArrayList;

public class ReleaseStmt implements IStmt {
    private String var;

    public ReleaseStmt(String s) {
        this.var = s;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> symTable = state.getSymTable();
        ISemaphoreTable semTable = state.getSemaphoreTable();
        Integer prgIndex = state.getPrgId();
        IADTStack<IStmt> exeStack = state.getExeStack();

        if (!symTable.containsKey(this.var)) {
            throw new StmtException("Variable of name " + this.var + "doesn't exist");
        }

        if (!symTable.lookup(this.var).getType().equals(new IntType())) {
            throw new StmtException("Variable of name " + this.var + "isn't of type Integer");
        }

        IValue IValueIndex = symTable.lookup(this.var);
        IntValue foundIndex = (IntValue) IValueIndex;

        if (!semTable.containsAddress(foundIndex.getValue())) {
            throw new StmtException("Index " + foundIndex.getValue() + " not in semaphore table");
        }

        Pair<Integer, ArrayList<Integer>> pair = semTable.returnPair(foundIndex.getValue());

        if (pair.getValue().contains(prgIndex)) {
            pair.getValue().remove(prgIndex);
        }

        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType varType = typeEnv.lookup(this.var);

        if (!(varType.equals(new IntType()))){
            throw new TypeCheckException("Variable of ReleaseStmt is not Integer");
        }
        return typeEnv;
    }

    public IStmt deepCopy() {
        return new ReleaseStmt(this.var);
    }

    public String toString() {
        return "release(" + this.var + ")";
    }
}
