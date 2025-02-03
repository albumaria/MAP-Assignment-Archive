package model.prgstate;

import model.adt.dictionary.IADTDictionary;
import model.values.IntValue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface ILatchTable {
    public void addToLatchTable(IntValue value);
    public Integer getLastKey();
    public Integer getLatchValue(Integer index);
    public boolean containsKey(Integer index);
    public void updateLatchEntry(Integer index, IntValue value);
    public String toString();
    public Map<Integer, IntValue> getContent();
}
