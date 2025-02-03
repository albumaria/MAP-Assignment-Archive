package model.statements;

import javafx.util.Pair;
import model.TypeCheckException;
import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.expressions.IExp;
import model.prgstate.IHeap;
import model.prgstate.ISemaphoreTable;
import model.prgstate.PrgState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.ArrayList;

public class CreateSemStmt implements IStmt {
    private String var;
    private IExp exp;

    public CreateSemStmt(String s, IExp e) {
        this.var = s;
        this.exp = e;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        IValue number1 = this.exp.evaluate(symTable, heap);

        if (!number1.getType().equals(new IntType())) {
            throw new StmtException("Type of expression in CreateSemStmt not Integer");
        }

        IntValue number2 = (IntValue) number1;

        if (!symTable.containsKey(this.var)) {
            throw new StmtException("Variable of name " + this.var + "doesn't exist");
        }

        if (!symTable.lookup(this.var).getType().equals(new IntType())) {
            throw new StmtException("Variable of name " + this.var + "isn't of type Integer");
        }

        ISemaphoreTable semTable = state.getSemaphoreTable();
        Pair<Integer, ArrayList<Integer>> pairToAdd = new Pair<>(number2.getValue(), new ArrayList<Integer>());
        semTable.addToSemTable(pairToAdd);
        IValue lastIndex = new IntValue(semTable.getLastAddress() + 1);
        symTable.put(this.var, lastIndex);
        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType varType = typeEnv.lookup(this.var);
        IType expType = this.exp.typeCheck(typeEnv);

        if (!(varType.equals(new IntType()) || expType.equals(new IntType()))){
            throw new TypeCheckException("Variable and Expression of CreateSemStmt are not Integer");
        }
        return typeEnv;
    }

    public IStmt deepCopy() {
        return new CreateSemStmt(this.var, this.exp.deepCopy());
    }

    public String toString() {
        return "createSemaphore(" + this.var + ", " + this.exp + ")";
    }
}
