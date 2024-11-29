package model.expressions;

import model.adt.dictionary.IADTDictionary;
import model.prgstate.IHeap;
import model.statements.StmtException;
import model.values.IValue;
import model.values.RefValue;

public class HeapReadExp implements IExp {
    private IExp expression;

    public HeapReadExp(IExp e) {
        this.expression = e;
    }

    public IValue evaluate(IADTDictionary<String, IValue> table, IHeap heap) throws ExpException {
        IValue expValue = this.expression.evaluate(table, heap);

        if (!(expValue instanceof RefValue)) {
            throw new StmtException("Expression " + expValue.toString() + " not of type RefType");
        }

        RefValue ref = (RefValue) expValue;
        int address = ref.getAddress();

        if(!heap.containsAddress(address))
            throw new StmtException("Invalid address to read from");

        IValue value = heap.returnValue(address);

        return value;
    }

    public IExp deepCopy() {
        return new HeapReadExp(this.expression.deepCopy());
    }

    public String toString() {
        return "rH(" + this.expression + ")";
    }
}
