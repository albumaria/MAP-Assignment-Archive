package model.statements;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.adt.list.IADTList;
import model.prgstate.IProcTable;
import model.prgstate.PrgState;
import model.prgstate.ProcTable;
import model.types.IType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProcDefStmt implements IStmt {
    private String name;
    private IADTList<String> varList;
    private IStmt body;

    public ProcDefStmt(String n, IADTList<String> varList, IStmt body) {
        this.name = n;
        this.varList = varList;
        this.body = body;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IProcTable procTable = state.getProcTable();
        Map<IADTList<String>, IStmt> tuple = new ConcurrentHashMap<>();
        tuple.put(varList, body);
        procTable.addToHeap(name, tuple);

        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        return typeEnv;
    }

    public IStmt deepCopy() {
        return new ProcDefStmt(this.name, this.varList, this.body.deepCopy());
    }

    public String toString() {
        return "def " + this.name + "(" + this.varList.getContent().toString() + ") " + this.body.toString();
    }
}
