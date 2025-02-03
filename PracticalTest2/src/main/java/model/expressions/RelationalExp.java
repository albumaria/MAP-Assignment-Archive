package model.expressions;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.prgstate.IHeap;
import model.types.BoolType;
import model.types.IType;
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

    public IType typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType type1, type2;
        type1 = this.leftExp.typeCheck(typeEnv);
        type2 = this.rightExp.typeCheck(typeEnv);
        if (type1.equals(integer)) {
            if (type2.equals(integer)) {
                return new BoolType();
            } else {
                throw new TypeCheckException("Second operand of Relational Expression is not of type int");
            }
        }
        else {
            throw new TypeCheckException("First operand of Relational Expression is not of type int");
        }
    }

    public IExp deepCopy() {
        return new RelationalExp(new String(this.op), this.leftExp.deepCopy(), this.rightExp.deepCopy());
    }

    public String toString() {
        return this.leftExp + " " +  this.op + " " + this.rightExp;
    }
}
