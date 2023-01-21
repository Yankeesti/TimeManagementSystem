package com.heinsberg.LearningManager.Gui.controller.componentController;
import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StudyInformationController extends BaseInformationComponentController implements Initializable {


    @FXML
    private Label titleLabel;

    private Study study;

    public StudyInformationController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName) {
        super(studyManager, viewFactory, fxmlName);

    }

    /**
     * should only get a Study Object
     * @param study - Study to update Information
     */
    @Override
    public void upDateInformation(Object study) throws ClassCastException{
        if(study.getClass() == Study.class){
        this.study = (Study) study;
        titleLabel.setText(((Study) study).getName());
        }
        else{
            throw new ClassCastException("Object must be from type Study");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(studyManager.getStudy() != null){
            titleLabel.setText(studyManager.getStudy().getName());}
    }
}
