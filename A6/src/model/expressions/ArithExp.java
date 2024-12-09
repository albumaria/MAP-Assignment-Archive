package model.expressions;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.prgstate.IHeap;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class ArithExp implements IExp {
    private IExp leftExp, rightExp;
    private char op;
    private static IntType integer = new IntType();

    public ArithExp(char o, IExp l, IExp r) {
        this.op = o;
        this.leftExp = l;
        this.rightExp = r;
    }

    public IValue evaluate(IADTDictionary<String, IValue> table, IHeap heap) throws ExpException {
        IValue leftValue = this.leftExp.evaluate(table, heap);
        IValue rightValue = this.rightExp.evaluate(table, heap);

        if (! leftValue.getType().equals(integer))
            throw new ExpException("Left expression not of type int for arithmetic expression");

        if (! rightValue.getType().equals(integer))
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

    public IType typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType type1, type2;
        type1 = this.leftExp.typeCheck(typeEnv);
        type2 = this.rightExp.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else {
                throw new TypeCheckException("Second operand of Arithmetic Expression is not of type int");
            }
        }
        else {
            throw new TypeCheckException("First operand of Arithmetic Expression is not of type int");
        }
    }

    public String toString() {
        String operation = " " + this.op + " ";
        return this.leftExp + operation + this.rightExp;
    }

    public IExp deepCopy() {
        return new ArithExp(this.op, this.leftExp.deepCopy(), this.rightExp.deepCopy());
    }
}
