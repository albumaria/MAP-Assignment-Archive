package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.adt.stack.IADTStack;
import model.expressions.IExp;
import model.expressions.RelationalExp;
import model.prgstate.PrgState;
import model.types.BoolType;
import model.types.IType;

public class SwitchStmt implements IStmt {
    private IExp exp;
    private IExp exp1;
    private IExp exp2;
    private IStmt stmt1;
    private IStmt stmt2;
    private IStmt stmt3;

    public SwitchStmt(IExp exp, IExp exp1, IExp exp2, IStmt stmt1, IStmt stmt2, IStmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IfStmt newStmt = new IfStmt(new RelationalExp("==", this.exp, this.exp1), this.stmt1,
                new IfStmt(new RelationalExp("==", this.exp, this.exp2), this.stmt2, this.stmt3));

        IADTStack<IStmt> exeStack = state.getExeStack();
        exeStack.push(newStmt);

        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType expT = this.exp.typeCheck(typeEnv);
        IType exp1T = this.exp1.typeCheck(typeEnv);
        IType exp2T = this.exp2.typeCheck(typeEnv);

        if (!(expT.equals(exp1T) || exp1T.equals(exp2T) || exp2T.equals(expT))){
            throw new TypeCheckException("Type of conditions not equal");
        }

        this.stmt1.typeCheck(typeEnv);
        this.stmt2.typeCheck(typeEnv);
        this.stmt3.typeCheck(typeEnv);
        return typeEnv;
    }

    public IStmt deepCopy() {
        return new SwitchStmt(this.exp.deepCopy(), this.exp1.deepCopy(), this.exp2.deepCopy(), this.stmt1.deepCopy(), this.stmt2.deepCopy(), this.stmt3.deepCopy());
    }

    public String toString() {
        return "switch(" + this.exp + ") (case " + this.exp1 + ": " + this.stmt1 + ") (case " + this.exp2 + ": " + this.stmt2 + ") (default: " + this.stmt3 + ")";
    }
}
