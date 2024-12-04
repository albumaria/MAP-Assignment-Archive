package model.statements;

import model.adt.dictionary.IADTDictionary;
import model.adt.list.IADTList;
import model.adt.stack.IADTStack;
import model.expressions.IExp;
import model.prgstate.IHeap;
import model.prgstate.PrgState;
import model.values.IValue;

public class PrintStmt implements IStmt{
    private IExp exp;

    public PrintStmt(IExp e) {
        this.exp = e;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTStack<IStmt> stack = state.getExeStack();
        IHeap heap = state.getHeap();
        //stack.pop();
        IADTList<IValue> outs = state.getOutput();
        IADTDictionary<String, IValue> table = state.getSymTable();
        IValue val = exp.evaluate(table, heap);
        outs.add(val);

        return null;
    }

    public IStmt deepCopy() {
        return new PrintStmt(this.exp.deepCopy());
    }

    public String toString() {
        return "Print(" + this.exp.toString() + ")";
    }
}
