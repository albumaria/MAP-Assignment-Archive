package model.prgstate;

import model.values.StringValue;

import java.io.BufferedReader;

public interface IFileTable {
    public boolean containsFile(StringValue fileName);
    public BufferedReader returnReader(StringValue fileName);
    public void openFile(StringValue fileName, BufferedReader fileReader);
    public void closeFile(StringValue fileName);
    public String toString();
}
