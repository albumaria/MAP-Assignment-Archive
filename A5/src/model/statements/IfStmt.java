package model.statements;

import model.adt.dictionary.IADTDictionary;
import model.expressions.IExp;
import model.prgstate.IHeap;
import model.prgstate.PrgState;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class IfStmt implements IStmt {
    private IExp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(IExp e, IStmt t, IStmt el) {
        this.exp = e;
        this.thenS = t;
        this.elseS = el;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> sym = state.getSymTable();
        IHeap heap = state.getHeap();
        IValue val = exp.evaluate(sym, heap);

        if(!val.getType().equals(new BoolType()))
            throw new StmtException("Type mismatch in if statement");

        BoolValue v = (BoolValue) val;
        if(v.getValue())
            state.getExeStack().push(thenS);
        else
            state.getExeStack().push(elseS);

        return null;
    }

    public String toString() {
        return "if (" + exp.toString() + ") " + "{ then " + thenS.toString()  + " else " + elseS.toString() + " }";
    }

    public IStmt deepCopy() {
        return new IfStmt(this.exp.deepCopy(), this.thenS.deepCopy(), this.elseS.deepCopy());
    }

}



