package main;

import controller.Controller;
import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.adt.list.ADTList;
import model.adt.list.IADTList;
import model.adt.stack.ADTStack;
import model.adt.stack.IADTStack;
import model.expressions.ArithExp;
import model.expressions.ValueExp;
import model.expressions.VarExp;
import model.prgstate.PrgState;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        IADTStack<IStmt> exeStack1 = new ADTStack<IStmt>();
        exeStack1.push(ex1);
        IADTDictionary<String, IValue> symTable1 = new ADTDictionary<String, IValue>();
        IADTList<IValue> output1 = new ADTList<IValue>();
        IADTDictionary<StringValue, BufferedReader> fileTable1 = new ADTDictionary<StringValue, BufferedReader>();
        IStmt originalPrg1 = ex1.deepCopy();
        PrgState prg1 = new PrgState(exeStack1, symTable1, output1, fileTable1, originalPrg1);
        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller c1 = new Controller(repo1);



        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        IADTStack<IStmt> exeStack2 = new ADTStack<IStmt>();
        exeStack2.push(ex2);
        IADTDictionary<String, IValue> symTable2 = new ADTDictionary<String, IValue>();
        IADTList<IValue> output2 = new ADTList<IValue>();
        IADTDictionary<StringValue, BufferedReader> fileTable2 = new ADTDictionary<StringValue, BufferedReader>();
        IStmt originalPrg2 = ex2.deepCopy();
        PrgState prg2 = new PrgState(exeStack2, symTable2, output2, fileTable2, originalPrg2);
        IRepository repo2 = new Repository(prg2, "log2.txt");
        Controller c2 = new Controller(repo2);



        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));
        IADTStack<IStmt> exeStack3 = new ADTStack<IStmt>();
        exeStack3.push(ex3);
        IADTDictionary<String, IValue> symTable3 = new ADTDictionary<String, IValue>();
        IADTList<IValue> output3 = new ADTList<IValue>();
        IADTDictionary<StringValue, BufferedReader> fileTable3 = new ADTDictionary<StringValue, BufferedReader>();
        IStmt originalPrg3 = ex3.deepCopy();
        PrgState prg3 = new PrgState(exeStack3, symTable3, output3, fileTable3, originalPrg3);
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller c3 = new Controller(repo3);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit!"));
        menu.addCommand(new RunExample("1", ex1.toString(), c1));
        menu.addCommand(new RunExample("2", ex2.toString(), c2));
        menu.addCommand(new RunExample("3", ex3.toString(), c3));
        menu.show();

    }
}
