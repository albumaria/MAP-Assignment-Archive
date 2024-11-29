package main;

import com.sun.jdi.Value;
import controller.Controller;
import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.adt.list.ADTList;
import model.adt.list.IADTList;
import model.adt.stack.ADTStack;
import model.adt.stack.IADTStack;
import model.expressions.*;
import model.prgstate.*;
import model.statements.*;
import model.statements.file_statements.OpenRFile;
import model.statements.file_statements.ReadFile;
import model.statements.file_statements.CloseRFile;
import model.statements.heap_statements.HeapWriteStmt;
import model.statements.heap_statements.NewStmt;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
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
        IFileTable fileTable1 = new FileTable();
        IHeap heap1 = new Heap();
        IStmt originalPrg1 = ex1.deepCopy();
        PrgState prg1 = new PrgState(exeStack1, symTable1, output1, fileTable1, heap1, originalPrg1);
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
        IFileTable fileTable2 = new FileTable();
        IHeap heap2 = new Heap();
        IStmt originalPrg2 = ex2.deepCopy();
        PrgState prg2 = new PrgState(exeStack2, symTable2, output2, fileTable2, heap2, originalPrg2);
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
        IFileTable fileTable3 = new FileTable();
        IHeap heap3 = new Heap();
        IStmt originalPrg3 = ex3.deepCopy();
        PrgState prg3 = new PrgState(exeStack3, symTable3, output3, fileTable3, heap3, originalPrg3);
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller c3 = new Controller(repo3);

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))), new CompStmt(
                        new OpenRFile(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()),
                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"), new CompStmt(
                                new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));

        IADTStack<IStmt> exeStack4 = new ADTStack<IStmt>();
        exeStack4.push(ex4);
        IADTDictionary<String, IValue> symTable4 = new ADTDictionary<String, IValue>();
        IADTList<IValue> output4 = new ADTList<IValue>();
        IFileTable fileTable4 = new FileTable();
        IHeap heap4 = new Heap();
        IStmt originalPrg4 = ex4.deepCopy();
        PrgState prg4 = new PrgState(exeStack4, symTable4, output4, fileTable4, heap4, originalPrg4);
        IRepository repo4 = new Repository(prg4, "log4.txt");
        Controller c4 = new Controller(repo4);

        IStmt ex5 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a"))
                                        )
                                )
                        )
                )
        );

        IADTStack<IStmt> exeStack5 = new ADTStack<IStmt>();
        exeStack5.push(ex5);
        IADTDictionary<String, IValue> symTable5 = new ADTDictionary<String, IValue>();
        IADTList<IValue> output5 = new ADTList<IValue>();
        IFileTable fileTable5 = new FileTable();
        IHeap heap5 = new Heap();
        IStmt originalPrg5 = ex5.deepCopy();
        PrgState prg5 = new PrgState(exeStack5, symTable5, output5, fileTable5, heap5, originalPrg5);
        IRepository repo5 = new Repository(prg5, "log5.txt");
        Controller c5 = new Controller(repo5);

        IStmt ex6 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1))))),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );

        IADTStack<IStmt> exeStack6 = new ADTStack<IStmt>();
        exeStack6.push(ex6);
        IADTDictionary<String, IValue> symTable6 = new ADTDictionary<String, IValue>();
        IADTList<IValue> output6 = new ADTList<IValue>();
        IFileTable fileTable6 = new FileTable();
        IHeap heap6 = new Heap();
        IStmt originalPrg6 = ex6.deepCopy();
        PrgState prg6 = new PrgState(exeStack6, symTable6, output6, fileTable6, heap6, originalPrg6);
        IRepository repo6 = new Repository(prg6, "log6.txt");
        Controller c6 = new Controller(repo6);

        IStmt ex7 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new HeapReadExp(new VarExp("v"))),
                                new CompStmt(
                                        new HeapWriteStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new HeapReadExp(new VarExp("v")), new ValueExp(new IntValue(5))))
                                )
                        )
                )
        );

        IADTStack<IStmt> exeStack7 = new ADTStack<IStmt>();
        exeStack7.push(ex7);
        IADTDictionary<String, IValue> symTable7 = new ADTDictionary<String, IValue>();
        IADTList<IValue> output7 = new ADTList<IValue>();
        IFileTable fileTable7 = new FileTable();
        IHeap heap7 = new Heap();
        IStmt originalPrg7 = ex7.deepCopy();
        PrgState prg7 = new PrgState(exeStack7, symTable7, output7, fileTable7, heap7, originalPrg7);
        IRepository repo7 = new Repository(prg7, "log7.txt");
        Controller c7 = new Controller(repo7);

        IStmt ex8 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new HeapReadExp(new HeapReadExp(new VarExp("a"))))
                                        )
                                )
                        )
                )
        );

        IADTStack<IStmt> exeStack8 = new ADTStack<IStmt>();
        exeStack8.push(ex8);
        IADTDictionary<String, IValue> symTable8 = new ADTDictionary<String, IValue>();
        IADTList<IValue> output8 = new ADTList<IValue>();
        IFileTable fileTable8 = new FileTable();
        IHeap heap8 = new Heap();
        IStmt originalPrg8 = ex8.deepCopy();
        PrgState prg8 = new PrgState(exeStack8, symTable8, output8, fileTable8, heap8, originalPrg8);
        IRepository repo8 = new Repository(prg8, "log8.txt");
        Controller c8 = new Controller(repo8);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit!"));
        menu.addCommand(new RunExample("1", ex1.toString(), c1));
        menu.addCommand(new RunExample("2", ex2.toString(), c2));
        menu.addCommand(new RunExample("3", ex3.toString(), c3));
        menu.addCommand(new RunExample("4", ex4.toString(), c4));
        menu.addCommand(new RunExample("5", ex5.toString(), c5));
        menu.addCommand(new RunExample("6", ex6.toString(), c6));
        menu.addCommand(new RunExample("7", ex7.toString(), c7));
        menu.addCommand(new RunExample("8", ex8.toString(), c8));
        menu.show();

    }
}
