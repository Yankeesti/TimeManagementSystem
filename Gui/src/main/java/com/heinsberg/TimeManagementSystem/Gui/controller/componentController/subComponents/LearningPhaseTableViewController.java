package com.heinsberg.TimeManagementSystem.Gui.controller.componentController.subComponents;

import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.BaseController;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class LearningPhaseTableViewController extends BaseController implements Initializable {

    @FXML
    protected TableView<LearningPhase> learningPhaseView;
    @FXML
    protected TableColumn<LearningPhase, Date> dateColumn;
    @FXML
    protected TableColumn<LearningPhase, Long> learnedColumn;
    @FXML
    protected TableColumn<LearningPhase, Void> actionColumn;

    @FXML
    protected TableColumn<LearningPhase,String> subjectColumn;

    public LearningPhaseTableViewController(ContentManager contentManager, ViewFactory viewFactory) {
        super(contentManager, viewFactory, "/com/heinsberg/TimeManagementSystem/Gui/controller/subComponents/TableView");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpDateColumn();
        setUpLearnedColumn();
        setUpActionColumn();
        setUpSubjectColumn();
    }

    public void showSubjectColumn(boolean p){
        //subjectColumn.setVisible(p);
    }

    /**
     * Sets up the Subject column
     */
    private void setUpSubjectColumn() {
        subjectColumn.setCellValueFactory(cellData ->{
            LearningPhase learningPhase = cellData.getValue();
            return new SimpleStringProperty(learningPhase.getTimeSpentContainer().getName());
        });
    }

    /**
     * Sets up the Action Column where a Item can be deleted
     */
    private void setUpActionColumn(){
        Callback<TableColumn<LearningPhase, Void>, TableCell<LearningPhase, Void>> cellFactory = new Callback<TableColumn<LearningPhase, Void>, TableCell<LearningPhase, Void>>() { // new Cell Factory that is called when a new Cell needs to be created
            @Override
            public TableCell<LearningPhase, Void> call(TableColumn<LearningPhase, Void> learningPhaseVoidTableColumn) {//is called to create a Table cell for the column learningPhaseVoidTableColumn
                final TableCell<LearningPhase, Void> outPut = new TableCell<LearningPhase, Void>() {
                    private final Button deleteButton = new Button("Delete");

                    {//is called when instance of class is created ( everytime a new cell is "Renderd
                        deleteButton.setOnAction(e -> {
                            LearningPhase data = getTableView().getItems().get(getIndex());
                            viewFactory.getDialogViewFactory().showDeleteLearningPhaseDialog(data);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
                return outPut;
            }
        };

        actionColumn.setCellFactory(cellFactory);
        actionColumn.setSortable(false);
    }

    /**
     * sets up the learned Column
     */
    private void setUpLearnedColumn(){
        //set up Learned Column
        learnedColumn.setCellValueFactory(new PropertyValueFactory<>("timeLearned"));
        learnedColumn.setCellFactory(column -> new TableCell<LearningPhase, Long>() { //Formats the showing of time learned to "... Minuten"
            @Override
            protected void updateItem(Long learned, boolean empty) {
                super.updateItem(learned, empty);
                if (learned == null || empty) {
                    setText(null);
                } else {
                    learned /= 60;//time learned in Minutes
                    setText(learned + " Minuten");
                }
            }
        });
        learnedColumn.setSortable(false);
    }




    /**
     * Sets up the Date Column
     */
    private void setUpDateColumn(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy 'um' HH:mm 'Uhr'");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        dateColumn.setCellFactory(column -> new TextFieldTableCell<LearningPhase, Date>() {//is called to format the cell
            @Override
            public void updateItem(Date date, boolean empty) {//when item is updated this method is called
                super.updateItem(date, empty);
                if (date == null || empty) {
                    setText(null);
                } else {
                    setText(dateFormat.format(date));
                }
            }
        });
        dateColumn.setSortType(TableColumn.SortType.DESCENDING);
    }

    /**
     * Displays the given LearningPhases in the Table View
     * @param learningPhases - LearningPhases to show
     */
    public void displayLearningPhases(ObservableList<LearningPhase> learningPhases){
        learningPhaseView.setItems(learningPhases);
        learningPhases.addListener(new ListChangeListener<LearningPhase>() {
            @Override
            public void onChanged(Change<? extends LearningPhase> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        learningPhaseView.getSortOrder().clear();
                        learningPhaseView.getSortOrder().add(dateColumn);
                        learningPhaseView.sort();
                    }
                }
            }
        });
    }

    /**
     *
     * @return the Node of the Table view
     */
    public Node getNode() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName + ".fxml"));
        fxmlLoader.setController(this);
        Node node;
        try {
            node = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return node;
    }
}
