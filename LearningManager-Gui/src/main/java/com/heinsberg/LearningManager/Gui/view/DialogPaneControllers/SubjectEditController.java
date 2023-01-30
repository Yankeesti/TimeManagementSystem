package com.heinsberg.LearningManager.Gui.view.DialogPaneControllers;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import javafx.collections.FXCollections;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SubjectEditController extends BaseController implements Initializable {

    @FXML
    private Spinner<Integer> ectsChooser;

    @FXML
    private Spinner<Integer> hourChooser;

    @FXML
    private Spinner<Integer> minuteChooser;

    @FXML
    private TextField subjectNameField;
    @FXML
    private CheckBox ungradedCheckBox;
    @FXML
    private Spinner<Double> gradeSpinner;
    @FXML
    private DialogPane dialogPane;
    private ButtonType deleteButton;
    private Subject subject;

    public SubjectEditController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName, Subject subject) {
        super(studyManager, viewFactory, fxmlName);
        this.subject = subject;
    }

    public void submitChanges() {
        if (ungradedCheckBox.isSelected())
            subject.editInformation(subjectNameField.getText(), ectsChooser.getValue(), hourChooser.getValue(), minuteChooser.getValue(), Double.MAX_VALUE);
        else
            subject.editInformation(subjectNameField.getText(), ectsChooser.getValue(), hourChooser.getValue(), minuteChooser.getValue(), gradeSpinner.getValue());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpSpinners();
        setUpNameField();
        setUpUngradedCheckbox();
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

    private void setUpUngradedCheckbox() {
        if (subject.getFinalGrade() == Double.MAX_VALUE) {
            ungradedCheckBox.setSelected(true);
        }
        ungradedCheckBox.setOnAction((evt) -> {
            if (ungradedCheckBox.isSelected()) {//checkBox is selected make gradeSpinner unclickable
                gradeSpinner.setDisable(true);
            } else {
                gradeSpinner.setDisable(false);
            }
        });
    }

    private void setUpNameField() {
        subjectNameField.setText(subject.getSubjectName());
    }

    private void setUpSpinners() {
        //SetUp EctsPoint Spinner
        SpinnerValueFactory ectsValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20);
        ectsValueFactory.setValue(subject.getEctsPoints());
        ectsChooser.setValueFactory(ectsValueFactory);

        //setUp hourChooser
        SpinnerValueFactory hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 70);
        hourValueFactory.setValue(subject.getWeekGoal() / 60);
        hourChooser.setValueFactory(hourValueFactory);

        //setUp minuteChooser
        SpinnerValueFactory minuteValueFactory = new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int i) {//when decrementing and under 0 start by 60
                if (getValue() - i < 0)
                    setValue(60);
                else
                    setValue(getValue() - 1);
            }

            @Override
            public void increment(int i) { // when incrementing and over 60 start by 0
                if (getValue() + i > 60)
                    setValue(0);
                else
                    setValue(getValue() + i);
            }
        };
        minuteValueFactory.setValue(subject.getWeekGoal() % 60);
        minuteChooser.setValueFactory(minuteValueFactory);

        //Set up Grade Spinner
        SpinnerValueFactory.DoubleSpinnerValueFactory gradSpinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 6);
        if (subject.getFinalGrade() == Double.MAX_VALUE) {//if Subject is ungraded
            gradSpinnerValueFactory.setValue(null);
            gradeSpinner.setDisable(true);
        } else if (subject.getFinalGrade() == 0) {//subject grade is not set yet
            gradSpinnerValueFactory.setValue(3.0);
        } else {//Final grade is set --> set predefined Value
            gradSpinnerValueFactory.setValue(subject.getFinalGrade());
        }
        gradSpinnerValueFactory.setAmountToStepBy(0.1);
        gradeSpinner.setValueFactory(gradSpinnerValueFactory);
    }

}
