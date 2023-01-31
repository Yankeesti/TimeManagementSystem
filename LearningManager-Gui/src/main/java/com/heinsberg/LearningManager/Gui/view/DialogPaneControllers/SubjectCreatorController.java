package com.heinsberg.LearningManager.Gui.view.DialogPaneControllers;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The SubjectCreateorController is made to controll a Subject create dialog.
 */
public class SubjectCreatorController extends SubjectManipulateBaseController implements Initializable {
    private Button okButton;
    private Semester semester;

    public SubjectCreatorController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName, Semester semester) {
        super(studyManager, viewFactory, fxmlName);
        this.semester = semester;
    }

    public Subject getSelectedStudy() {
        if(!subjectNameField.getText().isEmpty()){
            Subject outPut = new Subject(subjectNameField.getText(),semester,ectsChooser.getValue());
            outPut.setWeekGoal(hourChooser.getValue()*60+minuteChooser.getValue());
            if(!currentlyUngradedCheckBox.isSelected()){//when grade is set set that grade for subject
                double grade;
                if(ungradedCheckBox.isSelected())
                    grade = Double.MAX_VALUE;
                else
                    grade = gradeSpinner.getValue();
                outPut.setFinalGrade(grade);
            }
            return outPut;
        }
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpBasic();
        setUpSpinners();
        setUpNameField();
        setUpOkButton();
        setUpCheckBoxes();
    }

    private void setUpCheckBoxes() {
        currentlyUngradedCheckBox.setSelected(true);
    }

    private void setUpOkButton() {
        okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        okButton.setText("Erstellen");

    }

    /**
     * Checks weather all neceserry Inputs were made if yes okButton gets enabled
     */
    private void checkInput() {
        if (!subjectNameField.getText().isEmpty()) {
            okButton.setDisable(false);
        } else {
            okButton.setDisable(true);
        }

    }

    private void setUpNameField() {
        subjectNameField.setPromptText("Fach Name");
        subjectNameField.textProperty().addListener((observable) -> {
            checkInput(); //when text is edited Check inoput
        });
    }

    /**
     * Sets up the Spinners with predefined Values
     * also makes ungradedCheckBox and gradeSpinner disabled
     */
    private void setUpSpinners() {
        ectsValueFactory.setValue(5);
        hourValueFactory.setValue(0);
        minuteValueFactory.setValue(0);
        gradeSpinner.setDisable(true);
    }
}
