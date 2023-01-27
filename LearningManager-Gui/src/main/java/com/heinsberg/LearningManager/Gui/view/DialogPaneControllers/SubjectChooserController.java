package com.heinsberg.LearningManager.Gui.view.DialogPaneControllers;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
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
    private ChoiceBox<Subject> subjectChooser;
    private ObservableList<Subject> subjects;



    public SubjectChooserController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName, ObservableList subjects) {
        super(studyManager, viewFactory, fxmlName);
        this.subjects = subjects;
    }


    public Subject getSelected(){
        return subjectChooser.getValue();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpSubjectChooser();
    }

    private void setUpSubjectChooser() {
        subjectChooser.setItems(subjects);
        if(!subjects.isEmpty())
        subjectChooser.setValue(subjects.get(0));
    }
    public DialogPane getSubjectChooserDialogPane() {
        return subjectChooserDialogPane;
    }
}