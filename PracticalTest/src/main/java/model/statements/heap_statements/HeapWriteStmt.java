package model.statements.heap_statements;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.IExp;
import model.prgstate.IHeap;
import model.prgstate.PrgState;
import model.statements.IStmt;
import model.statements.StmtException;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class HeapWriteStmt implements IStmt {
    private String variableName;
    private IExp expression;

    public HeapWriteStmt(String varName, IExp exp) {
        this.variableName = varName;
        this.expression = exp;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();

        if (!symTable.containsKey(this.variableName)) {
            throw new StmtException("Variable " + this.variableName + " not declared");
        }

        if(!(symTable.lookup(this.variableName).getType() instanceof RefType)) {
            throw new StmtException("Variable " + this.variableName+ " not RefType");
        }

        RefValue refValue = (RefValue) symTable.lookup(this.variableName);
        RefType tableType = (RefType) symTable.lookup(this.variableName).getType();

        if(!heap.containsAddress(refValue.getAddress())) {
            throw new StmtException("Heap does not contain address " + refValue.getAddress());
        }

        IValue expEvaluation = this.expression.evaluate(symTable, heap);

        if(!expEvaluation.getType().equals(tableType.getInner())) {
            throw new StmtException("Expression does not match location type");
        }

        heap.updateHeap(refValue.getAddress(), expEvaluation);

        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType typeVar = typeEnv.lookup(this.variableName);
        IType typeExp = this.expression.typeCheck(typeEnv);

        if(typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        }
        else throw new TypeCheckException("Right hand side and left hand side of Heap Write Statement have different types");
    }

    public IStmt deepCopy() {
        return new HeapWriteStmt(new String(this.variableName), this.expression.deepCopy());
    }

    public String toString() {
        return "wH(" + this.variableName + ", " + this.expression.toString() + ")";
    }
}
