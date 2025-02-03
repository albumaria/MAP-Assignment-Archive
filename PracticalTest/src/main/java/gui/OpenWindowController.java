package gui;

import com.sun.jdi.Value;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.*;
import model.adt.dictionary.*;
import model.statements.file_statements.CloseRFile;
import model.statements.file_statements.OpenRFile;
import model.statements.file_statements.ReadFile;
import model.statements.heap_statements.HeapWriteStmt;
import model.statements.heap_statements.NewStmt;
import model.values.*;
import model.statements.*;
import model.types.*;
import model.expressions.*;

import java.io.IOException;
import java.util.*;

public class OpenWindowController {
    private List<IStmt> allStmts = new ArrayList<IStmt>();

    @FXML
    private Button startButton;

    @FXML
    private ListView<String> programListView;

    @FXML
    private void initialize() {
        populateAllProgramsList();
    }

    @FXML
    private void startButtonHandler() throws IOException {
        String selectedItem = this.programListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showErrorWindow("ERROR", "Must select a program first");
        }
        else {
            for (IStmt stmt : this.allStmts) {
                if (stmt.toString().equals(selectedItem)) {
                    PrgWindowController prgWindow = new PrgWindowController();
                    prgWindow.initialize(stmt);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PrgWindow.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(loader.load(), 1000, 800);
                    stage.setTitle("Program Window");
                    stage.setScene(scene);
                    PrgWindowController prgWindowController = loader.getController();
                    prgWindowController.initialize(stmt);
                    stage.show();
                }
            }
        }
    }

    private void showErrorWindow(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null); // You can add a header if needed
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void populateAllProgramsList() {
        ObservableList<String> programs = FXCollections.observableArrayList();

        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(20))), new PrintStmt(new VarExp("v"))));

        try {
            IADTDictionary<String, IType> typeEnv1 = new ADTDictionary<String, IType>();
            ex1.typeCheck(typeEnv1);
            programs.add(ex1.toString());
            this.allStmts.add(ex1);
        }
        catch (TypeCheckException tcE) {
            System.out.println("TypeCheckException: " + tcE + " - First program not added to the list");
        }

        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"),
                                        new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));

        try {
            IADTDictionary<String, IType> typeEnv2 = new ADTDictionary<String, IType>();
            ex2.typeCheck(typeEnv2);
            programs.add(ex2.toString());
            this.allStmts.add(ex2);
        }
        catch (TypeCheckException tcE) {
            System.out.println("TypeCheckException: " + tcE + " - Second program not added to the list");
        }

        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));

        try {
            IADTDictionary<String, IType> typeEnv3 = new ADTDictionary<String, IType>();
            ex3.typeCheck(typeEnv3);
            programs.add(ex3.toString());
            this.allStmts.add(ex3);
        }
        catch (TypeCheckException tcE) {
            System.out.println("TypeCheckException: " + tcE + " - Third program not added to the list");
        }

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))), new CompStmt(
                        new OpenRFile(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()),
                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"), new CompStmt(
                                new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));

        try {
            IADTDictionary<String, IType> typeEnv4 = new ADTDictionary<String, IType>();
            ex4.typeCheck(typeEnv4);
            programs.add(ex4.toString());
            this.allStmts.add(ex4);
        }
        catch (TypeCheckException tcE) {
            System.out.println("TypeCheckException: " + tcE + " - Fourth program not added to the list");
        }

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

        try {
            IADTDictionary<String, IType> typeEnv5 = new ADTDictionary<String, IType>();
            ex5.typeCheck(typeEnv5);
            programs.add(ex5.toString());
            this.allStmts.add(ex5);
        }
        catch (TypeCheckException tcE) {
            System.out.println("TypeCheckException: " + tcE + " - Fifth program not added to the list");
        }

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

        try {
            IADTDictionary<String, IType> typeEnv6 = new ADTDictionary<String, IType>();
            ex6.typeCheck(typeEnv6);
            programs.add(ex6.toString());
            this.allStmts.add(ex6);
        }
        catch (TypeCheckException tcE) {
            System.out.println("TypeCheckException: " + tcE + " - Sixth program not added to the list");
        }

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

        try {
            IADTDictionary<String, IType> typeEnv7 = new ADTDictionary<String, IType>();
            ex7.typeCheck(typeEnv7);
            programs.add(ex7.toString());
            this.allStmts.add(ex7);
        }
        catch (TypeCheckException tcE) {
            System.out.println("TypeCheckException: " + tcE + " - Seventh program not added to the list");
        }

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

        try {
            IADTDictionary<String, IType> typeEnv8 = new ADTDictionary<String, IType>();
            ex8.typeCheck(typeEnv8);
            programs.add(ex8.toString());
            this.allStmts.add(ex8);
        }
        catch (TypeCheckException tcE) {
            System.out.println("TypeCheckException: " + tcE + " - Eighth program not added to the list");
        }

        IStmt ex9 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new forkStmt(
                                                        new CompStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new HeapReadExp(new VarExp("a")))
                                                                        )
                                                                ))
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new HeapReadExp(new VarExp("a")))
                                                )

                                        )
                                )
                        )
                )
        );

        try {
            IADTDictionary<String, IType> typeEnv9 = new ADTDictionary<String, IType>();
            ex9.typeCheck(typeEnv9);
            programs.add(ex9.toString());
            this.allStmts.add(ex9);
        }
        catch (TypeCheckException tcE) {
            System.out.println("TypeCheckException: " + tcE + " - Ninth program not added to the list");
        }

        IStmt ex10 = new CompStmt(
                new forkStmt(
                        new CompStmt(
                                new VarDeclStmt("a", new IntType()),
                                new AssignStmt("a", new ValueExp(new IntValue(20)))
                        )
                ),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new AssignStmt("b", new ValueExp(new IntValue(35)))
                )
        );

        try {
            IADTDictionary<String, IType> typeEnv10 = new ADTDictionary<String, IType>();
            ex10.typeCheck(typeEnv10);
            programs.add(ex10.toString());
            this.allStmts.add(ex10);
        }
        catch (TypeCheckException tcE) {
            System.out.println("TypeCheckException: " + tcE + " - Tenth program not added to the list");
        }

        IStmt ex11 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new VarDeclStmt("c", new IntType()),
                                new CompStmt(
                                    new AssignStmt("a", new ValueExp(new IntValue(1))),
                                    new CompStmt(
                                            new AssignStmt("b", new ValueExp(new IntValue(2))),
                                            new CompStmt(
                                                    new AssignStmt("c", new ValueExp(new IntValue(5))),
                                                    new CompStmt(
                                                            new SwitchStmt(
                                                                    new ArithExp('*', new VarExp("a"), new ValueExp(new IntValue(10))),
                                                                    new ArithExp('*', new VarExp("b"), new VarExp("c")),
                                                                    new ValueExp(new IntValue(10)),
                                                                    new CompStmt(
                                                                            new PrintStmt(new VarExp("a")),
                                                                            new PrintStmt(new VarExp("b"))
                                                                    ),
                                                                    new CompStmt(
                                                                            new PrintStmt(new ValueExp(new IntValue(100))),
                                                                            new PrintStmt(new ValueExp(new IntValue(200)))
                                                                    ),
                                                                    new PrintStmt(new ValueExp(new IntValue(300)))
                                                            ),
                                                            new PrintStmt(new ValueExp(new IntValue(300)))
                                                    )
                                            )
                                    )
                                )
                        )
                )
        );

        try {
            IADTDictionary<String, IType> typeEnv11 = new ADTDictionary<String, IType>();
            ex11.typeCheck(typeEnv11);
            programs.add(ex11.toString());
            this.allStmts.add(ex11);
        }
        catch (TypeCheckException tcE) {
            System.out.println("TypeCheckException: " + tcE + " - Eleventh program not added to the list");
        }

        programListView.setItems(programs);
    }

}
