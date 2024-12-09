package model.prgstate;

import model.values.IValue;
import model.values.StringValue;

import java.util.Map;

public interface IHeap {
    public boolean containsAddress(Integer address);
    public Integer getLastKey();
    public IValue returnValue(Integer address);
    public void addToHeap(IValue value);
    public void removeFromHeap(Integer address);
    public void updateHeap(Integer address, IValue value);
    public Integer getLastAddress();
    Map<Integer, IValue> getContent();
    void setContent(Map<Integer, IValue> newContent);
    public String toString();
}
