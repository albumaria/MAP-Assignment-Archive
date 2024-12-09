package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.expressions.IExp;
import model.prgstate.IHeap;
import model.prgstate.PrgState;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

import java.util.Dictionary;
import java.util.Map;

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

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType typeExp = this.exp.typeCheck(typeEnv);
        if(typeExp.equals(new BoolType())) {
            IADTDictionary<String, IType> typeEnvCopy = new ADTDictionary<String, IType>();
            Map<String, IType> typeEnvContent = typeEnv.getContent();
            for(Map.Entry<String, IType> entry : typeEnvContent.entrySet()) {
                IType aux = entry.getValue();
                typeEnvCopy.put(entry.getKey(), aux);
            }

            this.thenS.typeCheck(typeEnvCopy);
            this.elseS.typeCheck(typeEnvCopy);

            return typeEnv;
        }
        else
            throw new TypeCheckException("The condition of the If Statement is not of type boolean");
    }

    public String toString() {
        return "if (" + exp.toString() + ") " + "{ then " + thenS.toString()  + " else " + elseS.toString() + " }";
    }

    public IStmt deepCopy() {
        return new IfStmt(this.exp.deepCopy(), this.thenS.deepCopy(), this.elseS.deepCopy());
    }

}



