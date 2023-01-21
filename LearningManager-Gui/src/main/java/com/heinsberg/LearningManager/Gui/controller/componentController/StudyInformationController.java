package com.heinsberg.LearningManager.Gui.controller.componentController;
import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class StudyInformationController extends BaseController {


    @FXML
    Label testLabel;

    public StudyInformationController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName) {
        super(studyManager, viewFactory, fxmlName);
    }

//    public void setStudy(){
//        titelLabel.setText(studyManager.getStudy().getName());
//    }
}
