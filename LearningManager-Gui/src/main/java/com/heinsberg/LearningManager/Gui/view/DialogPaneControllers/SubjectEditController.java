package com.heinsberg.LearningManager.Gui.view.DialogPaneControllers;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

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
    private Subject subject;

    public SubjectEditController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName, Subject subject) {
        super(studyManager, viewFactory, fxmlName);
        this.subject = subject;
    }

    public void submitChanges() {
        subject.editInformation(subjectNameField.getText(),ectsChooser.getValue(),hourChooser.getValue(),minuteChooser.getValue());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpSpinners();
        setUpNameField();
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
        minuteValueFactory.setValue(subject.getWeekGoal()%60);
        minuteChooser.setValueFactory(minuteValueFactory);

    }
}
