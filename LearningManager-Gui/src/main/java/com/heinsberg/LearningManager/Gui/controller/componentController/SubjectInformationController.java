package com.heinsberg.LearningManager.Gui.controller.componentController;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SubjectInformationController extends BaseInformationComponentController {
    @FXML
    private Label titleLabel;
    private Subject subject;
    public SubjectInformationController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName) {
        super(studyManager, viewFactory, fxmlName);
    }

    @Override
    public void upDateInformation(Object subject) throws ClassCastException{
        if(subject.getClass() == Subject.class){
            this.subject = (Subject) subject;
            this.titleLabel.setText(this.subject.getSubjectName());
        }else{
            throw new ClassCastException("Object must be from type Subject");
        }

    }



}
