package model.statements.heap_statements;

import model.adt.dictionary.IADTDictionary;
import model.expressions.IExp;
import model.prgstate.IHeap;
import model.prgstate.PrgState;
import model.statements.IStmt;
import model.statements.StmtException;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class NewStmt implements IStmt {
    private String variableName;
    private IExp expression;

    public NewStmt(String var, IExp expr) {
        this.variableName = var;
        this.expression = expr;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> table = state.getSymTable();
        IHeap heap = state.getHeap();

        if (!table.containsKey(variableName))
            throw new StmtException("Variable " + this.variableName + " not declared");
        if (!(table.lookup(variableName).getType() instanceof RefType)) {
            throw new StmtException("Variable " + this.variableName + " not of type RefType");
        }

        IValue expValue = this.expression.evaluate(table, heap);
        RefType tableType = (RefType) table.lookup(this.variableName).getType();

        if(!(tableType.getInner().equals(expValue.getType()))) {
            throw new StmtException("Expression does not match type of variable");
        }

        heap.addToHeap(expValue);
        RefValue newValue = new RefValue(heap.getLastAddress(), tableType.getInner());
        table.put(this.variableName, newValue);

        return state;
    }

    public IStmt deepCopy() {
        return new NewStmt(new String(this.variableName), this.expression.deepCopy());
    }

    public String toString() {
        return "new(" + this.variableName + ", " + this.expression + ")";
    }
}
