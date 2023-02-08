package com.heinsberg.TimeManagementSystem.Gui.controller.componentController.TimeSpentContainterController;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.BaseInformationComponentController;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.SubjectListener;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseTimeSpentContainerController extends BaseInformationComponentController {

    @FXML
    protected Label titleLabel;
    @FXML
    protected Label weekGoalLabel, learnedLabel;
    @FXML
    protected TableView<LearningPhase> learningPhaseView;
    @FXML
    protected TableColumn<LearningPhase, Date> dateColum;
    @FXML
    protected TableColumn<LearningPhase, Long> learnedColum;
    @FXML
    protected TableColumn<LearningPhase, Void> actionCoulumn;
    @FXML
    protected GridPane learnedInformationPane;




    protected TimeSpentContainer shownObject;

    protected BaseTimeSpentContainerController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    /**
     * Method that needs to be called from Child class first when initialized
     * to set up the Basic Functionality of Information Node
     */
    public void setUpBase() {
        setUpLearningPhaseTableView();
    }

    /**
     * sets up a Listener to shown Object
     * when a change is detected showChanges is calle
     */
    private void setUpChangeListener() {
        shownObject.addListener(new SubjectListener() {
            @Override
            public void changed(SubjectChange subjectChange) {
                Platform.runLater(()->{
                    showChanges(subjectChange);
                });
            }
        });
    }

    protected abstract void showChanges(SubjectChange subjectChange);

    //Methods to Handle user Input
    @FXML
    void TimeSpentContainerSettingsAction(){
        TimeSpentContainerSettingsActionCalled();
    }

    /**
     * Method need to be implemented to handel TimeSpentContainerEdit call
     */
    protected abstract void TimeSpentContainerSettingsActionCalled();

    /**
     * Updates the information to be shown
     * a listner is added to shown object so when something changes in object the information i loaded to the Gui
     * calls the abstract Method setUpLearned
     *
     * @param objectToUpdate
     */
    public void upDateInformation(Object objectToUpdate) {
        if (objectToUpdate instanceof TimeSpentContainer) {
            this.shownObject = (TimeSpentContainer) objectToUpdate;
            this.titleLabel.setText(shownObject.getName());
            setUpChangeListener();
            setUpTimeProgressInformation();
            ObservableList<LearningPhase> learningPhases = shownObject.getLearningPhases();
            learningPhaseView.setItems(learningPhases);
            //sort learningPhases new when learningphase is added
            learningPhases.addListener(new ListChangeListener<LearningPhase>() {
                @Override
                public void onChanged(Change<? extends LearningPhase> change) {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            learningPhaseView.getSortOrder().clear();
                            learningPhaseView.getSortOrder().add(dateColum);
                            learningPhaseView.sort();
                        }
                    }
                }
            });
        } else {
            throw new ClassCastException("Object must be from type Subject");
        }
    }


    /**
     * needs to implemented by the child class
     * updates the information on the Time Progress ( Week Goal and time learned/worked)
     */
    protected abstract void setUpTimeProgressInformation();

    /**
     * Setes the week Goal label depending on if a week goal is set or not
     */
    protected void setWeekGoalLable() {
        int weekGoal = shownObject.getWeekGoal();
        if (weekGoal > 0)//when weekGoal is set show goal
        {
            int[] time = getInHoursAndMinutes(shownObject.getWeekGoal());
            weekGoalLabel.setText(time[0] + " Stunden und " + time[1] + " Minuten ");
            weekGoalLabel.setStyle("-fx-background-color: transparent;");

        } else {
            weekGoalLabel.setStyle("-fx-background-color: red;");
            weekGoalLabel.setText("Es wurde noch kein Wochen Ziel Festgelegt");
        }
    }

    /**
     * Sets up the TableView
     */
    private void setUpLearningPhaseTableView() {
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
     * caluculates a time from Minutes to hours and mintes
     *
     * @param minutes - Time in Minutets
     * @return - [0] hours [1] Minutes
     */
    protected int[] getInHoursAndMinutes(int minutes) {
        return new int[]{minutes / 60, minutes % 60};
    }

}
