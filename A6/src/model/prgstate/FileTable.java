package model.prgstate;

import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.values.StringValue;
import java.io.BufferedReader;

public class FileTable implements IFileTable {
    private IADTDictionary<StringValue, BufferedReader> fileTable;

    public FileTable() {
        this.fileTable = new ADTDictionary<StringValue, BufferedReader>();
    }

    public FileTable(IADTDictionary<StringValue, BufferedReader> tbl) {
        this.fileTable = tbl;
    }

    public boolean containsFile(StringValue fileName) {
        return this.fileTable.containsKey(fileName);
    }

    public BufferedReader returnReader(StringValue fileName) {
        return this.fileTable.lookup(fileName);
    }

    public void openFile(StringValue fileName, BufferedReader fileReader) {
        this.fileTable.put(fileName, fileReader);
    }

    public void closeFile(StringValue fileName) {
        this.fileTable.remove(fileName);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        for (StringValue v : this.fileTable.keySet()) {
            s.append(v).append("\n");
        }

        return s.toString();
    }
}
