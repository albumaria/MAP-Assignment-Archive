package model.statements;

import model.adt.dictionary.IADTDictionary;
import model.expressions.IExp;
import model.prgstate.Heap;
import model.prgstate.IHeap;
import model.prgstate.PrgState;
import model.values.IValue;

public class AssignStmt implements IStmt {
    private String name;
    private IExp expression;

    public AssignStmt(String n, IExp e) {
        this.name = n;
        this.expression = e;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> table = state.getSymTable();
        //IADTStack<IStmt> stack = state.getExeStack();
        //stack.pop();
        IHeap heap = state.getHeap();

        if (table.containsKey(this.name)) {
            IValue v = this.expression.evaluate(table, heap);
            if (v.getType().toString().equals(table.lookup(this.name).getType().toString()))
                table.put(this.name, v);  // updates the value for the expression, this.name is already key
            else
                throw new StmtException("Type of expression and variable do not match");
        }
        else
            throw new StmtException("Variable " + this.name + " not declared");

        return state;
    }

    public IStmt deepCopy() {
        return new AssignStmt(new String(this.name), this.expression.deepCopy());
    }

    public String toString() {
        return this.name + " = " + this.expression.toString();
    }
}
