package com.heinsberg.TimeManagementSystem.Gui.controller.componentController;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.studyInformationControlls.SemesterSpinnerValueFactory;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.subComponents.LearningPhaseTableViewController;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class StudyInformationController extends BaseInformationComponentController implements Initializable {


    @FXML
    private Label titleLabel;
    @FXML
    private AnchorPane learningPhaseAnchorPane;

    private LearningPhaseTableViewController learningPhaseTableViewController;
    private Study study;

    public StudyInformationController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    /**
     * should only get a Study Object
     *
     * @param study - Study to update Information
     */
    @Override
    public void upDateInformation(Object study) throws ClassCastException {
        if (study.getClass() == Study.class) {
            this.study = (Study) study;
            titleLabel.setText(((Study) study).getName());
            learningPhaseTableViewController.displayLearningPhases(this.study.getLearningPhases());
        } else {
            throw new ClassCastException("Object must be from type Study");
        }
    }

    @Override
    public void refresh() {
        learningPhaseTableViewController.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpStudy();
        setUpLearningPhaseTableView();
    }

    private void setUpLearningPhaseTableView() {
        learningPhaseTableViewController = new LearningPhaseTableViewController(contentManager,viewFactory);
        Node tableView = learningPhaseTableViewController.getNode();
        learningPhaseAnchorPane.getChildren().add(tableView);
        learningPhaseAnchorPane.setTopAnchor(tableView,0.0);
        learningPhaseAnchorPane.setBottomAnchor(tableView, 0.0);
        learningPhaseAnchorPane.setRightAnchor(tableView, 0.0);
        learningPhaseAnchorPane.setLeftAnchor(tableView, 0.0);

    }

    /**
     * When a study is set the Title of the InformationPane is set to the name of the Study
     */
    private void setUpStudy() {
        if (study != null) {
            titleLabel.setText(study.getName());
        }
    }
}
