package controller;

import model.adt.ADTException;
import model.adt.stack.IADTStack;
import model.expressions.ExpException;
import model.prgstate.PrgState;
import model.statements.IStmt;
import model.statements.StmtException;
import repository.IRepository;

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
        System.out.println(program);

        try {
            while (!program.getExeStack().isEmpty()) {
                this.oneStep(program);
                System.out.println(program);
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
    }

}
