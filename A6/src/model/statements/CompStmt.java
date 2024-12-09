package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.adt.stack.IADTStack;
import model.prgstate.PrgState;
import model.types.IType;


public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    public String toString() {
        return first.toString() + " ; " + second.toString() + " ";
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTStack<IStmt> stack = state.getExeStack();
        stack.push(this.second);
        stack.push(this.first);

        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IADTDictionary<String, IType> typeEnv1 = this.first.typeCheck(typeEnv);
        IADTDictionary<String, IType> typeEnv2 = this.second.typeCheck(typeEnv1);
        return typeEnv2;
    }

    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }
}
