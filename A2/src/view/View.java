package view;

import controller.Controller;
import model.adt.list.ADTList;
import model.adt.list.IADTList;
import model.expressions.ArithExp;
import model.expressions.ValueExp;
import model.expressions.VarExp;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;
import repository.IRepository;
import repository.Repository;

import java.util.Scanner;

public class View {
    IADTList<Controller> exampleList;

    public void setUp() {
        this.exampleList = new ADTList<Controller>();
        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));

        IRepository repo1 = new Repository(ex1);
        Controller contr1 = new Controller(repo1);

        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        IRepository repo2 = new Repository(ex2);
        Controller contr2 = new Controller(repo2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));

        IRepository repo3 = new Repository(ex3);
        Controller contr3 = new Controller(repo3);

        this.exampleList.add(contr1);
        this.exampleList.add(contr2);
        this.exampleList.add(contr3);
    }

    public void printExamples() {
        String examples = """
                1) int v; v=2;Print(v)
                2) int a;int b; a=2+3*5;b=a+1;Print(b)
                3) bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)""";

        System.out.println(examples);
    }

    public void printMenu() {
        String menu = """
                1) Pick an example
                2) Complete execution """;

        System.out.println(menu);
    }

    public void executeExample(int number) {
        this.exampleList.get(number-1).allStep();
    }

    public void executeMain() {
        this.setUp();
        Scanner scanner = new Scanner(System.in);
        int option;
        int optionExample = -1;

        while(true) {
            this.printMenu();
            option = scanner.nextInt();

            // examples
            if (option == 1) {
                this.printExamples();
                optionExample = scanner.nextInt();
            }
            else if (option == 2) {
                if (optionExample == -1)
                    System.out.println("Pick an example first");
                else {
                    this.executeExample(optionExample);
                }
            }
            else
                System.out.println("Pick a valid option");

        }
    }

}
