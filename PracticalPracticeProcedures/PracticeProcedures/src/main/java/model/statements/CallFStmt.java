package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.adt.list.IADTList;
import model.adt.stack.IADTStack;
import model.expressions.IExp;
import model.prgstate.IHeap;
import model.prgstate.IProcTable;
import model.prgstate.PrgState;
import model.types.IType;
import model.values.IValue;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CallFStmt implements IStmt {
    private String fname;
    private IADTList<IExp> argumentList;

    public CallFStmt(String n, IADTList<IExp> args) {
        this.fname = n;
        this.argumentList = args;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IProcTable procTable = state.getProcTable();

        if (!procTable.containsFunction(this.fname)) {
            throw new StmtException("Function not in Procedure Table");
        }

        Map<IADTList<String>, IStmt> argList = procTable.getContent().get(this.fname);
        List<IADTList<String>> keySet = argList.keySet().stream().toList();
        IADTList<String> args = keySet.getFirst();
        IStmt body = argList.get(args);

        IADTDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        IADTDictionary<String, IValue> newSymTable = new ADTDictionary<>();

        for(int i = 0 ; i < args.size() ; i++) {
            IValue value = this.argumentList.get(i).evaluate(symTable, heap);

            newSymTable.put(args.get(i), value);
        }

        IADTStack<IADTDictionary<String, IValue>> symTableStack = state.getSymTableStack();
        symTableStack.push(newSymTable);
        IADTStack<IStmt> exeStack = state.getExeStack();
        exeStack.push(new ReturnStmt());
        exeStack.push(body);

        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        return typeEnv;
    }

    public IStmt deepCopy() {
        return new CallFStmt(this.fname, this.argumentList);
    }

    public String toString() {
        return "call " + this.fname + "(" + this.argumentList.getContent().toString() + ")";
    }
}
