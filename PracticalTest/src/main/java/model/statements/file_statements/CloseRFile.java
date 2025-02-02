package model.statements.file_statements;

import model.TypeCheckException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.IExp;
import model.prgstate.IFileTable;
import model.prgstate.IHeap;
import model.prgstate.PrgState;
import model.statements.IStmt;
import model.statements.StmtException;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private IExp expression;
    private static StringType string = new StringType();

    public CloseRFile(IExp e) {
        this.expression = e;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> symTable = state.getSymTable();
        IFileTable fileTable = state.getFileTable();
        IHeap heap = state.getHeap();

        IValue value = this.expression.evaluate(symTable, heap);
        if (!value.getType().toString().equals(string.toString())) {
            throw new StmtException("Close file argument is not of type string");
        }

        StringValue fileName = (StringValue) value;
        if(!fileTable.containsFile(fileName)) {
            throw new StmtException("File with name " + fileName + " was not opened/does not exist");
        }

        BufferedReader reader = fileTable.returnReader(fileName);
        try {
            reader.close();
        } catch (IOException e) {
            throw new StmtException("IO Exception has occurred when trying to close file " + fileName);
        }

        fileTable.closeFile(fileName);

        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType typeExp = this.expression.typeCheck(typeEnv);

        if(typeExp.equals(string)) {
            return typeEnv;
        }
        else throw new TypeCheckException("Variable of Open File Statement not of type string");
    }

    public IStmt deepCopy() {
        return new CloseRFile(this.expression.deepCopy());
    }

    public String toString() {
        return "closeRFile(" + this.expression + ")";
    }
}
