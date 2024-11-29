package controller;

import model.adt.ADTException;
import model.adt.stack.IADTStack;
import model.expressions.ExpException;
import model.prgstate.PrgState;
import model.statements.IStmt;
import model.statements.StmtException;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;

import java.util.*;
import java.util.stream.Collectors;


public class Controller {
    private IRepository repo;

    public Controller(IRepository r) {
        this.repo = r;
    }

    public PrgState oneStep(PrgState state) throws ControllerException {
        IADTStack<IStmt> stack = state.getExeStack();
        if(stack.isEmpty())
            throw new ControllerException("The execution stack is empty");

        IStmt currentStmt = stack.pop();

        return currentStmt.execute(state);
    }

    public void allStep() {
        PrgState program = this.repo.getCurrentPrg();
        this.repo.logPrgStateExec();

        try {
            while (!program.getExeStack().isEmpty()) {
                this.oneStep(program);
                this.repo.logPrgStateExec();
                program.getHeap().setContent(safeGarbageCollector(
                        getAddrFromSymTable(program.getSymTable().getContent().values()),
                        program.getHeap().getContent()));
                this.repo.logPrgStateExec();
            }
        }
        catch(ADTException adtE){
            System.out.println("ADT Error: " + adtE.getMessage() + "\n");
        }
        catch(ExpException expE){
            System.out.println("Expression Error: " + expE.getMessage()+ "\n");
        }
        catch(StmtException stmtE){
            System.out.println("Statement Error: " + stmtE.getMessage() + "\n");
        }
        catch(ControllerException ctrlE) {
            System.out.println("Controller Error: " + ctrlE.getMessage() + "\n");
        }

        this.repo.restartRepo();
    }

    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    List<Integer> getReferencedHeapAddresses(Map<Integer, IValue> heap) {
        return heap.values().stream()
                .filter(value -> value instanceof RefValue)
                .map(value -> ((RefValue) value).getAddress())
                .distinct()
                .collect(Collectors.toList());
    }

    Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap){
        List<Integer> heapReferencedAddresses = getReferencedHeapAddresses(heap);

        Set<Integer> allUsedAddresses = new HashSet<>(symTableAddr);
        allUsedAddresses.addAll(heapReferencedAddresses);

        return heap.entrySet().stream()
                .filter(e -> allUsedAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
