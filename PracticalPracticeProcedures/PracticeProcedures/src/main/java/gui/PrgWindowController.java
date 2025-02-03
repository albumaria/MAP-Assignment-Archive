package gui;

import controller.Controller;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.adt.list.ADTList;
import model.adt.list.IADTList;
import model.adt.stack.ADTStack;
import model.adt.stack.IADTStack;
import model.prgstate.*;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import java.util.*;

public class PrgWindowController {
    private IRepository repository;
    private Controller controller;
    private IHeap heap;
    private IADTList<IValue> output;
    private IFileTable fileTable;
    private Integer lastClickedId;
    private IProcTable procTable;

    @FXML
    private Label nrPrgStates = new Label();

    @FXML
    private TableView<HeapEntry> heapTableView = new TableView<HeapEntry>();

    @FXML
    private ListView<String> outListView = new ListView<String>();

    @FXML
    private ListView<String> fileTableListView = new ListView<String>();

    @FXML
    private ListView<String> prgStateIdListView = new ListView<String>();

    @FXML
    private TableView<SymTableEntry> symTableTableView = new TableView<SymTableEntry>();

    @FXML
    private TableView<ProcTableEntry> procTableTableView = new TableView<>();

    @FXML
    private ListView<String> exeStackListView = new ListView<String>();

    @FXML
    private Button oneStepButton;

    @FXML
    public void initialize(IStmt program) {
        IADTStack<IStmt> exeStack = new ADTStack<IStmt>();
        exeStack.push(program);
        IADTStack<IADTDictionary<String, IValue>> symTableStack = new ADTStack<>();
        symTableStack.push(new ADTDictionary<String, IValue>());
        this.output = new ADTList<IValue>();
        this.fileTable = new FileTable();
        this.heap = new Heap();
        this.procTable = new ProcTable();
        IStmt originalPrg = program.deepCopy();
        PrgState prg = new PrgState(exeStack, symTableStack, output, fileTable, heap, procTable, originalPrg);
        this.repository = new Repository(prg, "log.txt");
        this.lastClickedId = prg.getPrgId();
        this.controller = new Controller(this.repository);

        this.setHeapTableView();
        this.setOutListView();
        this.setFileTableListView();
        this.setPrgStateIdListView();
        this.setProcTableTableView();
        this.setExeStackListView(exeStack);
        this.setSymTableTableView(symTableStack);
        this.nrPrgStates.setText("Number of PrgStates: " + this.repository.getPrgList().size());
    }

    @FXML
    private void oneStepButtonHandler() {
        try {
            this.controller.oneStepForAllPrg(this.repository.getPrgList());
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        this.setHeapTableView();
        this.setOutListView();
        this.setFileTableListView();
        this.setPrgStateIdListView();
        this.setProcTableTableView();
        this.setSymTableTableView(this.repository.getPrgById(this.lastClickedId).getSymTableStack());
        this.setExeStackListView(this.repository.getPrgById(this.lastClickedId).getExeStack());
        this.nrPrgStates.setText("Number of PrgStates: " + this.repository.getPrgList().size());
    }

    @FXML
    private void idClickedHandler() {
        String selectedItem = this.prgStateIdListView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            this.lastClickedId = Integer.parseInt(selectedItem);
            PrgState prgState = this.repository.getPrgById(Integer.parseInt(selectedItem));

            this.setSymTableTableView(prgState.getSymTableStack());
            this.setExeStackListView(prgState.getExeStack());
        }
    }

    private void setHeapTableView() {
        ObservableList<HeapEntry> heapEntries = FXCollections.observableArrayList();

        for (Map.Entry<Integer, IValue> entry : this.heap.getContent().entrySet()) {
            heapEntries.add(new HeapEntry(entry.getKey(), entry.getValue()));
        }

        this.heapTableView.setItems(heapEntries);

        TableColumn<HeapEntry, Integer> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAddress()));

        TableColumn<HeapEntry, IValue> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));

        heapTableView.getColumns().setAll(addressColumn, valueColumn);
    }

    private void setProcTableTableView() {
        ObservableList<ProcTableEntry> procTableEntries = FXCollections.observableArrayList();

        for (Map.Entry<String, Map<IADTList<String>, IStmt>> entry : this.procTable.getContent().entrySet()) {
            procTableEntries.add(new ProcTableEntry(entry.getKey(), entry.getValue()));
        }

        this.procTableTableView.setItems(procTableEntries);

        TableColumn<ProcTableEntry, String> addressColumn = new TableColumn<>("Fname");
        addressColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));

        TableColumn<ProcTableEntry, Map<IADTList<String>, IStmt>> valueColumn = new TableColumn<>("Args");
        valueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));

        this.procTableTableView.getColumns().setAll(addressColumn, valueColumn);
    }

    private void setOutListView() {
        ObservableList<String> outData = FXCollections.observableArrayList();

        for(int i = 0; i < this.output.size(); i++) {
            outData.add(this.output.get(i).toString());
        }

        this.outListView.setItems(outData);

    }

    private void setFileTableListView() {
        ObservableList<String> fileData = FXCollections.observableArrayList();

        for(StringValue fileName : this.fileTable.getFileSet()) {
            fileData.add(fileName.toString());
        }

        this.fileTableListView.setItems(fileData);
    }

    private void setPrgStateIdListView() {
        Set<Integer> prgIdSet = this.repository.getPrgIdSet();
        ObservableList<String> prgIdData = FXCollections.observableArrayList();

        for(Integer i : prgIdSet) {
            prgIdData.add(i.toString());
        }
        this.prgStateIdListView.setItems(prgIdData);
    }

    private void setSymTableTableView(IADTStack<IADTDictionary<String, IValue>> symTableStack) {
        ObservableList<SymTableEntry> symTableEntries = FXCollections.observableArrayList();
        IADTDictionary<String, IValue> symTable = new ADTDictionary<>();
        if (!symTableStack.isEmpty()) {
            symTable = symTableStack.peek();
        }

        for (Map.Entry<String, IValue> entry : symTable.getContent().entrySet()) {
            symTableEntries.add(new SymTableEntry(entry.getKey(), entry.getValue()));
        }

        this.symTableTableView.setItems(symTableEntries);

        TableColumn<SymTableEntry, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));

        TableColumn<SymTableEntry, IValue> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));

        symTableTableView.getColumns().setAll(nameColumn, valueColumn);
    }

    private void setExeStackListView(IADTStack<IStmt> exeStack) {
        ObservableList<String> exeStackData = FXCollections.observableArrayList();
        List<IStmt> exeStackList = exeStack.getAllList();

        for(IStmt stmt : exeStackList.reversed()) {
            exeStackData.add(stmt.toString());
        }

        this.exeStackListView.setItems(exeStackData);
    }
}

