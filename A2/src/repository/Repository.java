package repository;

import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.adt.list.ADTList;
import model.adt.list.IADTList;
import model.adt.stack.ADTStack;
import model.adt.stack.IADTStack;
import model.prgstate.PrgState;
import model.statements.IStmt;
import model.values.IValue;

public class Repository implements IRepository {
    IADTList<PrgState> prgList;

    public Repository(IStmt originalProgram) {
        this.prgList = new ADTList<PrgState>();
        IADTStack<IStmt> stack = new ADTStack<IStmt>();
        stack.push(originalProgram);
        IADTDictionary<String, IValue> symTable = new ADTDictionary<String, IValue>();
        IADTList<IValue> output = new ADTList<IValue>();

        PrgState program = new PrgState(stack, symTable, output,originalProgram);
        this.prgList.add(program);
    }

    public PrgState getCurrentPrg() {
        return prgList.get(0);
    }

    public void logPrgStateExec() throws RepoException {
        // to implement
    }

    public void add(PrgState state) {
        this.prgList.add(state);
    }
}
