package com.heinsberg.LearningManager.Gui.controller.componentController;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SemesterInformationController extends BaseInformationComponentController{
    @FXML
    Label titleLabel;
    private Semester semester;
    public SemesterInformationController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName) {
        super(studyManager, viewFactory, fxmlName);
    }

    @Override
    public void upDateInformation(Object semester) throws ClassCastException{
        if(semester.getClass() == Semester.class){
            this.semester = (Semester) semester;
            titleLabel.setText(this.semester.getSemester()+". Semester");
        }
        else{
            throw new ClassCastException("Object must be from type Semester");
        }
    }

    public void setSemester(Semester semester){
        this.semester = semester;
        titleLabel.setText(semester.getSemester() +". Semester");
    }

}
