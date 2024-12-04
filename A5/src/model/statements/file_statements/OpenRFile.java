package model.statements.file_statements;

import model.adt.dictionary.IADTDictionary;
import model.expressions.IExp;
import model.prgstate.IFileTable;
import model.prgstate.IHeap;
import model.prgstate.PrgState;
import model.statements.IStmt;
import model.statements.StmtException;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt {
    private IExp expression;

    public OpenRFile(IExp e) {
        this.expression = e;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> symTable = state.getSymTable();
        IFileTable fileTable = state.getFileTable();
        IHeap heap = state.getHeap();
        IValue value = this.expression.evaluate(symTable, heap);

        if (!value.getType().toString().equals(new StringType().toString())) {
            throw new StmtException("Open file argument is not of type string");
        }
        StringValue fileName = (StringValue) value;

        if (fileTable.containsFile(fileName)) {
            throw new StmtException("File " + fileName + " is already opened");
        }

        BufferedReader fileReader;
        try {
            fileReader = new BufferedReader(new FileReader(fileName.getValue()));
        } catch (FileNotFoundException e) {
            throw new StmtException("File " + fileName + " to be opened does not exist");
        }

        fileTable.openFile(fileName, fileReader);

        return null;
    }

    public IStmt deepCopy() {
        return new OpenRFile(this.expression.deepCopy());
    }

    public String toString() {
        return "openRFile(" + this.expression + ")";
    }
}
