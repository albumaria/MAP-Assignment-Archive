package controller;

import model.adt.ADTException;
import model.adt.stack.IADTStack;
import model.expressions.ExpException;
import model.prgstate.PrgState;
import model.prgstate.PrgStateException;
import model.statements.IStmt;
import model.statements.StmtException;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

//TODO: Check here

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository r) {
        this.repo = r;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prg -> repo.logPrgStateExec(prg));

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep(); })).collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    }
                    catch (ExecutionException exeE) {
                        Throwable cause = exeE.getCause();
                        if (cause instanceof ADTException) {
                            System.out.println("ADT Error: " + cause.getMessage() + "\n");
                        } else if (cause instanceof ExpException) {
                            System.out.println("Expression Error: " + cause.getMessage() + "\n");
                        } else if (cause instanceof StmtException) {
                            System.out.println("Statement Error: " + cause.getMessage() + "\n");
                        } else if (cause instanceof ControllerException) {
                            System.out.println("Controller Error: " + cause.getMessage() + "\n");
                        } else if (cause instanceof PrgStateException) {
                            System.out.println("Program State Error: " + cause.getMessage() + "\n");
                        } else {
                            System.out.println("Unknown Execution Error: " + cause.getMessage() + "\n");
                        }
                    } catch (InterruptedException intE) {
                        System.out.println("Interruption Error: " + intE.getMessage() + "\n");
                    }

                    return null;
                })
                .filter(p -> p != null)
                .toList();

        prgList.addAll(newPrgList);

        //prgList.forEach(prg -> repo.logPrgStateExec(prg));
        this.repo.setPrgList(prgList);
    }


    public void allStep() {
        IStmt originalPrg = this.repo.getOriginalPrg();
        List<PrgState> prgList = removeCompletedPrg(this.repo.getPrgList());

        while(prgList.size() > 0) {
            try {
                //prgList.forEach(prg -> repo.logPrgStateExec(prg));
                this.oneStepForAllPrg(prgList);

                List<Integer> addressesInSymTables = this.getAllAddrFromSymTables();
                for (PrgState p : this.repo.getPrgList()) {
                    p.getHeap().setContent(safeGarbageCollector(
                            addressesInSymTables,
                            p.getHeap().getContent()));
                }

                prgList = this.removeCompletedPrg(this.repo.getPrgList());
            }
            catch (InterruptedException intE) {
                System.out.println("Interruption Error " + intE.getMessage() + "\n");
            }
        }

        executor.shutdownNow();
        this.repo.setPrgList(prgList);

        this.repo.restartRepo(originalPrg);
    }

    private List<Integer> getAllAddrFromSymTables() {
        return this.repo.getPrgList().stream()
                .flatMap(prg -> getAddrFromSymTable(prg.getSymTable().getContent().values()).stream())
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private List<Integer> getReferencedHeapAddresses(Map<Integer, IValue> heap) {
        return heap.values().stream()
                .filter(value -> value instanceof RefValue)
                .map(value -> ((RefValue) value).getAddress())
                .distinct()
                .collect(Collectors.toList());
    }

    private Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap){
        List<Integer> heapReferencedAddresses = getReferencedHeapAddresses(heap);

        Set<Integer> allUsedAddresses = new HashSet<>(symTableAddr);
        allUsedAddresses.addAll(heapReferencedAddresses);

        return heap.entrySet().stream()
                .filter(e -> allUsedAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


}
