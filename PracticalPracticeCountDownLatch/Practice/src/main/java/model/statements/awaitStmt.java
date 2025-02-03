package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.adt.stack.IADTStack;
import model.prgstate.ILatchTable;
import model.prgstate.PrgState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class awaitStmt implements IStmt {
    private String var;

    public awaitStmt(String var) {
        this.var = var;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> symTable = state.getSymTable();
        IADTStack<IStmt> exeStack = state.getExeStack();
        ILatchTable latchTable = state.getLatchTable();

        if(!symTable.containsKey(this.var)) {
            throw new StmtException("Value of name " + this.var + " not in SymTable");
        }

        IValue foundIndex = symTable.lookup(this.var);
        if (!foundIndex.getType().equals(new IntType())) {
            throw new StmtException("Index " + foundIndex.toString() + " not of type Integer");
        }
        IntValue intFoundIndex = (IntValue) foundIndex;
        if(!latchTable.containsKey(intFoundIndex.getValue())) {
            throw new StmtException("Index " + foundIndex.toString() + " not in latchTable");
        }

        if(latchTable.getLatchValue(intFoundIndex.getValue()) != 0) {
            exeStack.push(new awaitStmt(this.var));
        }

        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType typeVar = typeEnv.lookup(this.var);

        if(!typeVar.equals(new IntType())){
            throw new TypeCheckException("Variable not of type Integer");
        }

        return typeEnv;
    }

    public IStmt deepCopy() {
        return new awaitStmt(this.var);
    }

    public String toString() {
        return "await(" + this.var.toString() + ")";
    }
}
