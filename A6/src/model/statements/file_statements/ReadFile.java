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
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {
    private IExp expression;
    private String variableName;

    public ReadFile(IExp exp, String var) {
        this.expression = exp;
        this.variableName = var;
    }

    public PrgState execute(PrgState state) throws StmtException {
        IADTDictionary<String, IValue> symTable = state.getSymTable();
        IFileTable fileTable = state.getFileTable();
        IHeap heap = state.getHeap();

        if(!symTable.containsKey(this.variableName)) {
            throw new StmtException("Variable to be read into " + this.variableName + " not defined");
        }
        IValue variableValue = symTable.lookup(this.variableName);
        if(!variableValue.getType().equals(new IntType())) {
            throw new StmtException("Variable " + this.variableName + " not of type int");
        }

        IValue fileValue = this.expression.evaluate(symTable, heap);
        if(!fileValue.getType().equals(new StringType())) {
            throw new StmtException("File name argument not of type string");
        }

        StringValue fileStringValue = (StringValue) fileValue;
        if(!fileTable.containsFile(fileStringValue)) {
            throw new StmtException("File with name " + fileStringValue + " has not been opened/does not exist");
        }

        BufferedReader fileReader = fileTable.returnReader(fileStringValue);
        try {
            String line = fileReader.readLine();
            if (line == null) {
                symTable.put(this.variableName, new IntValue(0));
            }
            else {
                int readValue = Integer.parseInt(line);
                symTable.put(this.variableName, new IntValue(readValue));
            }

        } catch (IOException e) {
            throw new StmtException("IO Exception has occurred when trying to read from file " + fileStringValue);
        }

        return null;
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType typeVar = typeEnv.lookup(this.variableName);

        if(typeVar.equals(new IntType())) {
            return typeEnv;
        }
        else throw new TypeCheckException("Variable of Read File Statement not of type int");
    }

    public IStmt deepCopy() {
        return new ReadFile(this.expression.deepCopy(), new String(variableName));
    }

    public String toString() {
        return "readFile(" + this.expression + ", " + this.variableName + ")";
    }
}
