package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.IExp;
import model.prgstate.Heap;
import model.prgstate.IHeap;
import model.prgstate.PrgState;
import model.types.IType;
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

        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType typeVar = typeEnv.lookup(this.name);
        IType typeExp = this.expression.typeCheck(typeEnv);
        if (typeVar.equals(typeExp)) {
            return typeEnv;
        }
        else
            throw new TypeCheckException("Right hand side and left hand side of Assign Statement have different types");
    }

    public IStmt deepCopy() {
        return new AssignStmt(new String(this.name), this.expression.deepCopy());
    }

    public String toString() {
        return this.name + " = " + this.expression.toString();
    }
}
