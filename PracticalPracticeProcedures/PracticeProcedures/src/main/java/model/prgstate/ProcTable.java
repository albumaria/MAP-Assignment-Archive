package model.prgstate;

import model.adt.list.IADTList;
import model.statements.IStmt;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProcTable implements IProcTable {
    private Map<String, Map<IADTList<String>, IStmt>> procTable;

    public ProcTable() {
        this.procTable = new ConcurrentHashMap<String, Map<IADTList<String>, IStmt>>();
    }

    public boolean containsFunction(String name) {
        return this.procTable.containsKey(name);
    }

    public Map<IADTList<String>, IStmt> returnBody(String address) {
        return this.procTable.get(address);
    }

    public void addToHeap(String fname, Map<IADTList<String>, IStmt> body) {
        this.procTable.put(fname, body);
    }

    public Map<String, Map<IADTList<String>, IStmt>> getContent() {
        return this.procTable;
    }


    public void setContent(Map<String, Map<IADTList<String>, IStmt>> newContent) {
        this.procTable = newContent;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        for (String key : this.procTable.keySet()) {
            s.append(key).append(" -> ").append(this.procTable.get(key)).append("\n");
        }

        return s.toString();
    }
}
