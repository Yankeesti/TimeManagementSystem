package com.heinsberg.TimeManagementSystem.Gui.controller.componentController.TimeSpentContainterController;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.BaseInformationComponentController;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.subComponents.LearningPhaseTableViewController;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.SubjectListener;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
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
    protected GridPane learnedInformationPane;
    @FXML
    protected AnchorPane learningPhaseAnchorPane;

    protected LearningPhaseTableViewController learningPhaseTableView;



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
            learningPhaseTableView.displayLearningPhases(learningPhases);
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
        learningPhaseTableView = new LearningPhaseTableViewController(contentManager,viewFactory);
        Node tableView = learningPhaseTableView.getNode();
        learningPhaseAnchorPane.getChildren().add(tableView);
        learningPhaseTableView.showSubjectColumn(false);
        learningPhaseAnchorPane.setTopAnchor(tableView,0.0);
        learningPhaseAnchorPane.setBottomAnchor(tableView, 0.0);
        learningPhaseAnchorPane.setRightAnchor(tableView, 0.0);
        learningPhaseAnchorPane.setLeftAnchor(tableView, 0.0);
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

    @Override
    public void refresh(){
        learningPhaseTableView.refresh();
    }

}
