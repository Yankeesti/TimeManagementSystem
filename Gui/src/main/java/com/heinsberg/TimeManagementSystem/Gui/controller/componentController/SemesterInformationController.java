package com.heinsberg.TimeManagementSystem.Gui.controller.componentController;

import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.BaseController;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.subComponents.LearningPhaseTableViewController;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SemesterInformationController extends BaseInformationComponentController implements Initializable {
    @FXML
    Label titleLabel;
    @FXML
    private AnchorPane learningPhaseAnchorPane;

    private LearningPhaseTableViewController learningPhaseTableViewController;
    private Semester semester;
    public SemesterInformationController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    @Override
    public void upDateInformation(Object semester) throws ClassCastException{
        if(semester.getClass() == Semester.class){
            this.semester = (Semester) semester;
            titleLabel.setText(this.semester.getSemester()+". Semester");
            learningPhaseTableViewController.displayLearningPhases(((Semester) semester).getLearningPhases());
        }
        else{
            throw new ClassCastException("Object must be from type Semester");
        }
    }

    @Override
    public void refresh() {
        learningPhaseTableViewController.refresh();
    }

    public void setSemester(Semester semester){
        this.semester = semester;
        titleLabel.setText(semester.getSemester() +". Semester");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTableView();
    }

    private void setUpTableView() {
        learningPhaseTableViewController = new LearningPhaseTableViewController(contentManager,viewFactory);
        Node tableView = learningPhaseTableViewController.getNode();
        learningPhaseAnchorPane.getChildren().add(tableView);
        learningPhaseAnchorPane.setTopAnchor(tableView,0.0);
        learningPhaseAnchorPane.setBottomAnchor(tableView, 0.0);
        learningPhaseAnchorPane.setRightAnchor(tableView, 0.0);
        learningPhaseAnchorPane.setLeftAnchor(tableView, 0.0);
    }
}
