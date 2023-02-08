package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Subject;

import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.BaseController;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SubjectChooserController extends BaseController implements Initializable{


    @FXML
    private DialogPane subjectChooserDialogPane;

    @FXML
    private ChoiceBox<TimeSpentContainer> subjectChooser;
    private ObservableList<TimeSpentContainer> timeSpentContainers;



    public SubjectChooserController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName, ObservableList<TimeSpentContainer> timeSpentContainers) {
        super(contentManager, viewFactory, fxmlName);
        this.timeSpentContainers = timeSpentContainers;
    }


    public TimeSpentContainer getSelected(){
        return subjectChooser.getValue();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpSubjectChooser();
    }

    private void setUpSubjectChooser() {
        subjectChooser.setItems(timeSpentContainers);
        if(!timeSpentContainers.isEmpty())
        subjectChooser.setValue(timeSpentContainers.get(0));
    }
    public DialogPane getSubjectChooserDialogPane() {
        return subjectChooserDialogPane;
    }
}