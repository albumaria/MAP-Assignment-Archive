package model.statements.file_statements;

import model.adt.dictionary.IADTDictionary;
import model.expressions.IExp;
import model.prgstate.PrgState;
import model.statements.IStmt;
import model.statements.StmtException;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private IExp expression;

    public CloseRFile(IExp e) {
        this.expression = e;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> symTable = state.getSymTable();
        IADTDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        IValue value = this.expression.evaluate(symTable);
        if (!value.getType().equals(new StringType())) {
            throw new StmtException("Close file argument is not of type string");
        }

        StringValue fileName = (StringValue) value;
        if(!fileTable.containsKey(fileName)) {
            throw new StmtException("File with name " + fileName + " was not opened/does not exist");
        }

        BufferedReader reader = fileTable.lookup(fileName);
        try {
            reader.close();
        } catch (IOException e) {
            throw new StmtException("IO Exception has occurred when trying to close file " + fileName);
        }

        fileTable.remove(fileName);

        return state;
    }

    public IStmt deepCopy() {
        return new CloseRFile(this.expression.deepCopy());
    }

    public String toString() {
        return "closeRFile(" + this.expression + ")";
    }
}
