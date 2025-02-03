package model.prgstate;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISemaphoreTable {
    public Integer getLastKey();

    public boolean containsAddress(Integer address);

    public Pair<Integer, ArrayList<Integer>> returnPair(Integer address);

    public void removeFromSemTable(Integer address);

    public void updateSemTable(Integer address, Pair<Integer, ArrayList<Integer>> value);

    public Integer getLastAddress();

    public Map<Integer, Pair<Integer, ArrayList<Integer>>> getContent();

    public String toString() ;

    public void addToSemTable(Pair<Integer, ArrayList<Integer>> semEntry);
}
