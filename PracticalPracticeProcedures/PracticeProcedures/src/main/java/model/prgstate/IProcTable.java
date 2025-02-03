package model.prgstate;

import model.adt.list.IADTList;
import model.statements.IStmt;

import java.util.Map;

public interface IProcTable {
    public boolean containsFunction(String name);

    public Map<IADTList<String>, IStmt> returnBody(String address);

    public void addToHeap(String fname, Map<IADTList<String>, IStmt> body);

    public Map<String, Map<IADTList<String>, IStmt>> getContent();

    public void setContent(Map<String, Map<IADTList<String>, IStmt>> newContent);

    public String toString();
}
