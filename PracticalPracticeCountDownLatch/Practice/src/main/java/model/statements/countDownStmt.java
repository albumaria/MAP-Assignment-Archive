package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.adt.stack.IADTStack;
import model.expressions.ValueExp;
import model.prgstate.ILatchTable;
import model.prgstate.PrgState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class countDownStmt implements IStmt {
    private String var;

    public countDownStmt(String varName) {
        this.var = varName;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> symTable = state.getSymTable();
        IADTStack<IStmt> exeStack = state.getExeStack();
        ILatchTable latchTable = state.getLatchTable();
        Integer prgIndex = state.getPrgId();

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

        if(latchTable.getLatchValue(intFoundIndex.getValue()) > 0) {
            latchTable.updateLatchEntry(intFoundIndex.getValue(), new IntValue(latchTable.getLatchValue(intFoundIndex.getValue()) - 1));
        }

        exeStack.push(new PrintStmt(new ValueExp(new IntValue(prgIndex))));

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
        return new countDownStmt(this.var);
    }

    public String toString() {
        return "countDown(" + this.var + ")";
    }
}
