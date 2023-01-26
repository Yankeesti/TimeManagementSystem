package com.heinsberg.LearningManager.Gui.controller.componentController;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.controller.componentController.studyInformationControlls.SemesterSpinnerValueFactory;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class StudyInformationController extends BaseInformationComponentController implements Initializable {


    @FXML
    private Label titleLabel;
    @FXML
    private Spinner<Integer> semesterChooserSpinner;
    @FXML
    private Button createSemesterButton;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

    private Study study;

    public StudyInformationController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName) {
        super(studyManager, viewFactory, fxmlName);
        this.study = studyManager.getStudy();
    }

    /**
     * should only get a Study Object
     *
     * @param study - Study to update Information
     */
    @Override
    public void upDateInformation(Object study) throws ClassCastException {
        if (study.getClass() == Study.class) {
            this.study = (Study) study;
            titleLabel.setText(((Study) study).getName());
        } else {
            throw new ClassCastException("Object must be from type Study");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpSpinner();
        setUpStudy();
        setUpDatePicker();
    }

    @FXML
    void createSemesterAction() {
        Semester temp = getSemester();
        if(temp != null)
            studyManager.addNewSemester(temp);
    }

    /**
     * Creates a new Semester with the given data from startDatePicker, endDate Picker and semesterChooserSpinner
     *
     * @return new Semester or 0 when Date was false
     */
    private Semester getSemester() {
        if (startDatePicker.getValue() != null && endDatePicker.getValue() != null && semesterChooserSpinner.getValue() != null) {
            Date startDate = java.sql.Date.valueOf(startDatePicker.getValue());
            Date endDate = java.sql.Date.valueOf(endDatePicker.getValue());

            return new Semester(semesterChooserSpinner.getValue(),startDate,endDate);
        }else
            return null;
    }

    private void setUpDatePicker() {

    }

    private void upDateCreateSemesterButton() {
    }

    private void setUpStudy() {
        if (studyManager.getStudy() != null) {
            titleLabel.setText(studyManager.getStudy().getName());
        }
    }

    private void setUpSpinner() {
        SpinnerValueFactory valueFactory = new SemesterSpinnerValueFactory(study);
        valueFactory.setValue(1);
        semesterChooserSpinner.setValueFactory(valueFactory);
    }
}
