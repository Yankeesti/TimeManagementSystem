package com.heinsberg.TimeManagementSystem.Gui.controller.componentController.subComponents;

import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.BaseController;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class LearningPhaseTableViewController extends BaseController implements Initializable {

    @FXML
    protected TableView<LearningPhase> learningPhaseView;
    @FXML
    protected TableColumn<LearningPhase, Date> dateColum;
    @FXML
    protected TableColumn<LearningPhase, Long> learnedColum;
    @FXML
    protected TableColumn<LearningPhase, Void> actionCoulumn;

    public LearningPhaseTableViewController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpDateColumn();
        setUpLearnedColumn();
        setUpActionColum();
    }

    /**
     * Sets up the Action Column where a Item can be deleted
     */
    private void setUpActionColum(){
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

        actionCoulumn.setCellFactory(cellFactory);
        actionCoulumn.setSortable(false);
    }

    /**
     * sets up the learned Column
     */
    private void setUpLearnedColumn(){
        //set up Learned Column
        learnedColum.setCellValueFactory(new PropertyValueFactory<>("timeLearned"));
        learnedColum.setCellFactory(column -> new TableCell<LearningPhase, Long>() { //Formats the showing of time learned to "... Minuten"
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
        learnedColum.setSortable(false);
    }

    /**
     * Sets up the Date Column
     */
    private void setUpDateColumn(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy 'um' HH:mm 'Uhr'");
        dateColum.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        dateColum.setCellFactory(column -> new TextFieldTableCell<LearningPhase, Date>() {//is called to format the cell
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
        dateColum.setSortType(TableColumn.SortType.DESCENDING);
    }

    /**
     * Displays the given LearningPhases in the Table View
     * @param learningPhases - LearningPhases to show
     */
    public void displayLearningPhases(ObservableList<LearningPhase> learningPhases){
        learningPhaseView.setItems(learningPhases);
    }
}
