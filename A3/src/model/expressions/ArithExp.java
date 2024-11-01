package model.expressions;

import model.adt.dictionary.IADTDictionary;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class ArithExp implements IExp {
    private IExp leftExp, rightExp;
    private char op;

    public ArithExp(char o, IExp l, IExp r) {
        this.op = o;
        this.leftExp = l;
        this.rightExp = r;
    }

    public IValue evaluate(IADTDictionary<String, IValue> table) throws ExpException {
        IValue leftValue = this.leftExp.evaluate(table);
        IValue rightValue = this.rightExp.evaluate(table);

        if (! leftValue.getType().equals(new IntType()))
            throw new ExpException("Left expression not of type int for arithmetic expression");

        if (! rightValue.getType().equals(new IntType()))
            throw new ExpException("Right expression not of type int for arithmetic expression");

        IntValue intLeft = (IntValue) leftValue;
        IntValue intRight = (IntValue) rightValue;
        int left = intLeft.getValue(), right = intRight.getValue();

        if (op == '+')
            return new IntValue(left + right);
        if (op == '-')
            return new IntValue(left - right);
        if (op == '*')
            return new IntValue(left * right);
        if (op == '/') {
            if (right == 0)
                throw new ExpException("Division by zero error");
            else
                return new IntValue(left / right);
        }
        else
            throw new ExpException("Invalid operand for arithmetic expression");


    }

    public String toString() {
        String operation = " " + this.op + " ";
        return this.leftExp + operation + this.rightExp;
    }

    public IExp deepCopy() {
        return new ArithExp(this.op, this.leftExp.deepCopy(), this.rightExp.deepCopy());
    }
}
