package gui;

import controller.Controller;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
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

import java.lang.reflect.Array;
import java.util.*;

public class PrgWindowController {
    private IRepository repository;
    private Controller controller;
    private IHeap heap;
    private IADTList<IValue> output;
    private IFileTable fileTable;
    private Integer lastClickedId;
    private ISemaphoreTable semaphoreTable;

    @FXML
    private Label nrPrgStates = new Label();

    @FXML
    private TableView<HeapEntry> heapTableView = new TableView<HeapEntry>();

    @FXML
    private TableView<SemTableEntry> semTableView = new TableView<SemTableEntry>();

    @FXML
    private ListView<String> outListView = new ListView<String>();

    @FXML
    private ListView<String> fileTableListView = new ListView<String>();

    @FXML
    private ListView<String> prgStateIdListView = new ListView<String>();

    @FXML
    private TableView<SymTableEntry> symTableTableView = new TableView<SymTableEntry>();

    @FXML
    private ListView<String> exeStackListView = new ListView<String>();

    @FXML
    private Button oneStepButton;

    @FXML
    public void initialize(IStmt program) {
        IADTStack<IStmt> exeStack = new ADTStack<IStmt>();
        exeStack.push(program);
        IADTDictionary<String, IValue> symTable = new ADTDictionary<String, IValue>();
        this.output = new ADTList<IValue>();
        this.fileTable = new FileTable();
        this.heap = new Heap();
        this.semaphoreTable = new SemaphoreTable();
        IStmt originalPrg = program.deepCopy();
        PrgState prg = new PrgState(exeStack, symTable, output, fileTable, heap, semaphoreTable, originalPrg);
        this.repository = new Repository(prg, "log.txt");
        this.lastClickedId = prg.getPrgId();
        this.controller = new Controller(this.repository);

        this.setHeapTableView();
        this.setSemaphoreTable();
        this.setOutListView();
        this.setFileTableListView();
        this.setPrgStateIdListView();
        this.setExeStackListView(exeStack);
        this.setSymTableTableView(symTable);
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
        this.setSemaphoreTable();
        this.setOutListView();
        this.setFileTableListView();
        this.setPrgStateIdListView();
        this.setSymTableTableView(this.repository.getPrgById(this.lastClickedId).getSymTable());
        this.setExeStackListView(this.repository.getPrgById(this.lastClickedId).getExeStack());
        this.nrPrgStates.setText("Number of PrgStates: " + this.repository.getPrgList().size());
    }

    @FXML
    private void idClickedHandler() {
        String selectedItem = this.prgStateIdListView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            this.lastClickedId = Integer.parseInt(selectedItem);
            PrgState prgState = this.repository.getPrgById(Integer.parseInt(selectedItem));

            this.setSymTableTableView(prgState.getSymTable());
            this.setExeStackListView(prgState.getExeStack());
        }
    }

    private void setSemaphoreTable() {
        ObservableList<SemTableEntry> semEntries = FXCollections.observableArrayList();

        for (Map.Entry<Integer, Pair<Integer, ArrayList<Integer>>> entry : this.semaphoreTable.getContent().entrySet()) {
            semEntries.add(new SemTableEntry(entry.getKey(), entry.getValue()));
        }

        this.semTableView.setItems(semEntries);

        TableColumn<SemTableEntry, Integer> addressColumn = new TableColumn<>("Location");
        addressColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAddress()));

        TableColumn<SemTableEntry, Integer> valueColumn = new TableColumn<>("Number");
        valueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getKey()));

        TableColumn<SemTableEntry, ArrayList<Integer>> listColumn = new TableColumn<>("List");
        listColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getValue()));

        semTableView.getColumns().setAll(addressColumn, valueColumn, listColumn);
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

    private void setSymTableTableView(IADTDictionary<String, IValue> symTable) {
        ObservableList<SymTableEntry> symTableEntries = FXCollections.observableArrayList();

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

