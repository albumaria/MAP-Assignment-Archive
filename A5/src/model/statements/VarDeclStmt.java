package model.statements;

import model.adt.dictionary.IADTDictionary;
import model.adt.stack.IADTStack;
import model.prgstate.PrgState;
import model.types.IType;
import model.values.IValue;

public class VarDeclStmt implements IStmt {
    private String name;
    private IType type;

    public VarDeclStmt(String n, IType t) {
        this.name = n;
        this.type = t;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> table = state.getSymTable();
        IADTStack<IStmt> stack = state.getExeStack();

        if(table.containsKey(this.name))
            throw new StmtException("Variable" + this.name + "is already declared");

        table.put(this.name, this.type.defaultValue());

        return null;
    }

    public IStmt deepCopy() {
        return new VarDeclStmt(new String(this.name), this.type);
    }

    public String toString() {
        return this.type.toString() + " " + this.name;
    }
}
