package model.statements;

import model.adt.dictionary.IADTDictionary;
import model.adt.stack.IADTStack;
import model.expressions.IExp;
import model.prgstate.Heap;
import model.prgstate.IHeap;
import model.prgstate.PrgState;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class WhileStmt implements IStmt {
    private IExp condition;
    private IStmt instr;

    public WhileStmt(IExp exp, IStmt st) {
        this.condition = exp;
        this.instr = st;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        IADTStack<IStmt> stack = state.getExeStack();

        IValue exprEvaluation = this.condition.evaluate(symTable, heap);
        if(!exprEvaluation.getType().equals(new BoolType())) {
            throw new StmtException("While condition not of type bool");
        }

        BoolValue condition = (BoolValue) exprEvaluation;

        if(condition.getValue()) {
            stack.push(new WhileStmt(this.condition, this.instr));
            stack.push(this.instr);
        }

        return null;
    }

    public IStmt deepCopy() {
        return new WhileStmt(this.condition.deepCopy(), this.instr.deepCopy());
    }

    public String toString() {
        return "while(" + this.condition + ") { " + this.instr + " }";
    }
}
