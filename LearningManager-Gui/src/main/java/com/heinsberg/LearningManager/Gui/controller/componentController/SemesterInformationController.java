package com.heinsberg.LearningManager.Gui.controller.componentController;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SemesterInformationController extends BaseInformationComponentController implements Initializable{
    @FXML
    Label titleLabel;
    @FXML
    Button createSubjectButton;
    @FXML
    Spinner<Integer> ectsPointPicker;
    @FXML
    TextField subjectNameChooser;
    private Semester semester;
    public SemesterInformationController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName) {
        super(studyManager, viewFactory, fxmlName);
    }

    @FXML
    void createSubjectAction(){
        Subject newSubject = getSubject();
        if(newSubject != null)
        studyManager.addNewSubject(newSubject);
    }

    private Subject getSubject() {
        if(!subjectNameChooser.getText().isEmpty()){
            return new Subject(subjectNameChooser.getText(),semester,ectsPointPicker.getValue());
        }
        return null;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpEctsPointPicker();
    }

    private void setUpEctsPointPicker() {
        SpinnerValueFactory spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,Integer.MAX_VALUE);
        spinnerValueFactory.setValue(1);
        ectsPointPicker.setValueFactory(spinnerValueFactory);
    }
}
