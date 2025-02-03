package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.IExp;
import model.prgstate.IHeap;
import model.prgstate.ILatchTable;
import model.prgstate.PrgState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class newLatchStmt implements IStmt {
    private String var;
    private IExp exp;

    public newLatchStmt(String string, IExp exp) {
        this.var = string;
        this.exp = exp;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        ILatchTable latchTable = state.getLatchTable();

        if(!symTable.containsKey(this.var)) {
            throw new StmtException("Variable not in symTable");
        }
        if(!symTable.lookup(this.var).getType().equals(new IntType())) {
            throw new StmtException("Variable not of type Integer");
        }

        IValue value = this.exp.evaluate(symTable, heap);
        if (!value.getType().equals(new IntType())) {
            throw new StmtException("Value to evaluate not Integer");
        }

        latchTable.addToLatchTable((IntValue) value);
        symTable.put(this.var, new IntValue(latchTable.getLastKey()));

        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType typeVar = typeEnv.lookup(this.var);
        IType typeExp = this.exp.typeCheck(typeEnv);

        if (!typeVar.equals(new IntType())) {
            throw new TypeCheckException("Variable not of type Int");
        }

        if (!typeExp.equals(new IntType())) {
            throw new TypeCheckException("Expression not of type Int");
        }

        return typeEnv;
    }

    public IStmt deepCopy() {
        return new newLatchStmt(this.var, this.exp.deepCopy());
    }

    public String toString() {
        return "newLatch(" + this.var.toString() + ", " + this.exp.toString() + ")";
    }
}
