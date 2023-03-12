package com.heinsberg.TimeManagementSystem.Gui.controller.componentController.TimeSpentContainterController;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.BaseInformationComponentController;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.subComponents.charts.LearnProgressBarChartController;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.subComponents.LearningPhaseTableViewController;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.SubjectListener;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public abstract class BaseTimeSpentContainerController extends BaseInformationComponentController {

    @FXML
    protected Label titleLabel;
    @FXML
    protected Label weekGoalLabel, learnedLabel;
    @FXML
    protected GridPane learnedInformationPane;
    @FXML
    protected AnchorPane learningPhaseAnchorPane;

    @FXML
    protected AnchorPane mainPanelAnchorPane;
    protected LearningPhaseTableViewController learningPhaseTableView;
    protected LearnProgressBarChartController learnProgressBarChartController;
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
        setUpLearningPhaseProgressChart();
    }

    /**
     * Sets up the LearningPhase Progress Bar Chart
     */
    private void setUpLearningPhaseProgressChart() {
        learnProgressBarChartController = new LearnProgressBarChartController(contentManager,viewFactory);
        Node barChart = learnProgressBarChartController.getNode();
        mainPanelAnchorPane.getChildren().add(barChart);
        mainPanelAnchorPane.setTopAnchor(barChart, 80.0);
        mainPanelAnchorPane.setBottomAnchor(barChart, 0.0);
        mainPanelAnchorPane.setRightAnchor(barChart, 0.0);
        mainPanelAnchorPane.setLeftAnchor(barChart, 0.0);

    }

    /**
     * sets up a Listener to shown Object
     * when a change is detected showChanges is calle
     */
    private void setUpChangeListener() {
        shownObject.addListener(new SubjectListener() {
            @Override
            public void changed(SubjectChange subjectChange) {
                Platform.runLater(() -> {
                    showChanges(subjectChange);
                });
            }
        });
    }

    protected abstract void showChanges(SubjectChange subjectChange);

    //Methods to Handle user Input


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
            upDateBarChart();
        } else {
            throw new ClassCastException("Object must be from type Subject");
        }
    }

    private void upDateBarChart() {
        learnProgressBarChartController.showInformation(shownObject);
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
        learningPhaseTableView = new LearningPhaseTableViewController(contentManager, viewFactory);
        Node tableView = learningPhaseTableView.getNode();
        learningPhaseAnchorPane.getChildren().add(tableView);
        learningPhaseTableView.showSubjectColumn(false);
        learningPhaseAnchorPane.setTopAnchor(tableView, 0.0);
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
    public void refresh() {
        learningPhaseTableView.refresh();
    }

}
