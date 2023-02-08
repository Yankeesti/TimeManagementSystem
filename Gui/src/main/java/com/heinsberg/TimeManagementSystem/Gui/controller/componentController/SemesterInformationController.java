package com.heinsberg.TimeManagementSystem.Gui.controller.componentController;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SemesterInformationController extends BaseInformationComponentController{
    @FXML
    Label titleLabel;
    @FXML
    Button createSubjectButton;
    private Semester semester;
    public SemesterInformationController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    @FXML
    void createSubjectAction(){
        Subject newSubject = viewFactory.getDialogViewFactory().showSubjectCreator(semester);
        if(newSubject != null)
        contentManager.addNewSubject(semester.getStudy(),newSubject);
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
