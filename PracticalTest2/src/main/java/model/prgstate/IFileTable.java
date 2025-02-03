package model.prgstate;

import model.values.StringValue;

import java.io.BufferedReader;
import java.util.Set;

public interface IFileTable {
    public boolean containsFile(StringValue fileName);
    public BufferedReader returnReader(StringValue fileName);
    public void openFile(StringValue fileName, BufferedReader fileReader);
    public void closeFile(StringValue fileName);
    public Set<StringValue> getFileSet();
    public String toString();
}
