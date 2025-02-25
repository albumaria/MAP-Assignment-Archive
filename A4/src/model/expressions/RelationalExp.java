package model.expressions;

import model.adt.dictionary.IADTDictionary;
import model.prgstate.IHeap;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class RelationalExp implements IExp {
    private IExp leftExp, rightExp;
    private String op;
    private static IntType integer = new IntType();

    public RelationalExp(String o, IExp l, IExp r) {
        this.op = o;
        this.leftExp = l;
        this.rightExp = r;
    }

    public IValue evaluate(IADTDictionary<String, IValue> table, IHeap heap) throws ExpException {
        IValue leftIVal = this.leftExp.evaluate(table, heap);
        if (!leftIVal.getType().equals(integer)) {
            throw new ExpException("Left operand not of type int for relational expression");
        }

        IValue rightIVal = this.rightExp.evaluate(table, heap);
        if (!rightIVal.getType().equals(integer)) {
            throw new ExpException("Right operand not of type int for relational expression");
        }

        IntValue leftVal = (IntValue) leftIVal;
        IntValue rightVal = (IntValue) rightIVal;
        int left = leftVal.getValue(), right = rightVal.getValue();

        if (this.op.equals("<")) {
            return new BoolValue(left < right);
        }
        if (this.op.equals("<=")) {
            return new BoolValue(left <= right);
        }
        if (this.op.equals("==")) {
            return new BoolValue(left == right);
        }
        if (this.op.equals("!=")) {
            return new BoolValue(left != right);
        }
        if (this.op.equals(">")) {
            return new BoolValue(left > right);
        }
        if (this.op.equals(">=")) {
            return new BoolValue(left >= right);
        }
        else {
            throw new ExpException("Invalid operand for relational expression");
        }
    }

    public IExp deepCopy() {
        return new RelationalExp(new String(this.op), this.leftExp.deepCopy(), this.rightExp.deepCopy());
    }

    public String toString() {
        return this.leftExp + " " +  this.op + " " + this.rightExp;
    }
}
