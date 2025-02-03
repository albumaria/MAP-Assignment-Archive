package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.adt.stack.IADTStack;
import model.expressions.IExp;
import model.prgstate.PrgState;
import model.types.BoolType;
import model.types.IType;

public class CondAssignStmt implements IStmt {
    private String varName;
    private IExp exp1;
    private IExp exp2;
    private IExp exp3;

    public CondAssignStmt(String varName, IExp condition, IExp valExp1, IExp valExp2) {
        this.varName = varName;
        this.exp1 = condition;
        this.exp2 = valExp1;
        this.exp3 = valExp2;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTStack<IStmt> stack = state.getExeStack();

        stack.push(new IfStmt(exp1, new AssignStmt(varName, exp2), new AssignStmt(varName, exp3)));

        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType typeVar = typeEnv.lookup(this.varName);
        IType typeCond = this.exp1.typeCheck(typeEnv);
        IType typeAssign1 = this.exp2.typeCheck(typeEnv);
        IType typeAssign2 = this.exp3.typeCheck(typeEnv);

        if (!typeCond.equals(new BoolType())) {
            throw new TypeCheckException("Condition inside Conditional Assignment Statement not of type bool");
        }

        if (typeVar.equals(typeAssign1) && typeVar.equals(typeAssign2)) {
            return typeEnv;
        }
        else {
            throw new TypeCheckException("Expressions to assign to variable not of same type as variable");
        }

    }

    public String toString() {
        return this.varName + "=(" + this.exp1.toString() + ")?" + this.exp2.toString() + ":" + this.exp3.toString();
    }

    public IStmt deepCopy() {
        return new CondAssignStmt(this.varName, this.exp1.deepCopy(), this.exp2.deepCopy(), this.exp3.deepCopy());
    }
}
