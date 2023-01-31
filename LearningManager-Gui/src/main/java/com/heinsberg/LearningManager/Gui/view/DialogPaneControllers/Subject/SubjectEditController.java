package com.heinsberg.LearningManager.Gui.view.DialogPaneControllers.Subject;

import com.heinsberg.LearningManager.Gui.ContentManager;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.study.subject.Subject;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SubjectEditController extends SubjectManipulateBaseController implements Initializable {
    private ButtonType deleteButton;
    private Subject subject;

    public SubjectEditController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName, Subject subject) {
        super(contentManager, viewFactory, fxmlName);
        this.subject = subject;
    }

    public void submitChanges() {
            double grade;
            if(currentlyUngradedCheckBox.isSelected()){
                grade = 0.0;
            }else if(ungradedCheckBox.isSelected()){
                grade = Double.MAX_VALUE;
            }else{
                grade = gradeSpinner.getValue();
            }
            subject.editInformation(subjectNameField.getText(), ectsChooser.getValue(), hourChooser.getValue(), minuteChooser.getValue(), grade);
         }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpBasic();
        setUpSpinners();
        setUpCheckBoxes();
        setUpNameField();
        setUpDeleteButton();
    }



    /**
     * Creates a delete Button
     */
    private void setUpDeleteButton() {
        deleteButton = new ButtonType("Delete");
        dialogPane.getButtonTypes().add(deleteButton);
    }

    public ButtonType getDeleteButton() {
        return deleteButton;
    }

    private void setUpCheckBoxes() {
        if (subject.getFinalGrade() == Double.MAX_VALUE) {
            ungradedCheckBox.setSelected(true);
        }else if(subject.getFinalGrade() == 0.0){
            currentlyUngradedCheckBox.setSelected(true);
            ungradedCheckBox.setSelected(false);
            gradeSpinner.setDisable(true);
        }
    }

    private void setUpNameField() {
        subjectNameField.setText(subject.getSubjectName());
    }

    private void setUpSpinners() {
        //SetUp EctsPoint Spinner
        ectsValueFactory.setValue(subject.getEctsPoints());

        //setUp hourChooser
        hourValueFactory.setValue(subject.getWeekGoal() / 60);

        //setUp minuteChooser
        minuteValueFactory.setValue(subject.getWeekGoal() % 60);

        //Set up Grade Spinner
        if (subject.getFinalGrade() == Double.MAX_VALUE) {//if Subject is ungraded
            gradSpinnerValueFactory.setValue(null);
            gradeSpinner.setDisable(true);
        } else if (subject.getFinalGrade() == 0) {//subject grade is not set yet
            gradSpinnerValueFactory.setValue(0.0);
        } else {//Final grade is set --> set predefined Value
            gradSpinnerValueFactory.setValue(subject.getFinalGrade());
        }
    }

}
