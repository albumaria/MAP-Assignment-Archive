package model.expressions;

import model.adt.dictionary.IADTDictionary;
import model.prgstate.IHeap;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class LogicalExp implements IExp {
    private IExp leftExp, rightExp;
    private char op;
    private static BoolType bool = new BoolType();

    public LogicalExp(char o, IExp l, IExp r) {
        this.leftExp = l;
        this.rightExp = r;
        this.op = o;
    }

    public IValue evaluate(IADTDictionary<String, IValue> table, IHeap heap) throws ExpException {
        IValue leftValue = leftExp.evaluate(table, heap);
        IValue rightValue = rightExp.evaluate(table, heap);

        if(!(leftValue.getType()).equals(bool))
            throw new ExpException("Left expression not of type bool for logical expression");

        if(!(rightValue.getType()).equals(bool))
            throw new ExpException("Right expression not of type bool for logical expression");

        BoolValue boolLeft = (BoolValue) leftValue;
        BoolValue boolRight = (BoolValue) rightValue;

        if (op == '|')
            return new BoolValue(boolLeft.getValue() || boolRight.getValue());
        if (op == '&')
            return new BoolValue(boolLeft.getValue() && boolRight.getValue());
        else
            throw new ExpException("Invalid operand for logical expression");
    }

    public String toString() {
        String operation = " " + (this.op + this.op) + " ";
        return this.leftExp + operation + this.rightExp;
    }

    public IExp deepCopy() {
        return new LogicalExp(this.op, this.leftExp.deepCopy(), this.rightExp.deepCopy());
    }
}
