package com.heinsberg.TimeManagementSystem.Gui.controller.componentController;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.studyInformationControlls.SemesterSpinnerValueFactory;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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

    public StudyInformationController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
        this.study = contentManager.getStudy();
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
            contentManager.addNewSemester(temp);
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
        }else{
            System.out.println("Data for new Semester missing!");
            return null;}
    }

    private void setUpDatePicker() {

    }

    private void upDateCreateSemesterButton() {
    }

    private void setUpStudy() {
        if (contentManager.getStudy() != null) {
            titleLabel.setText(contentManager.getStudy().getName());
        }
    }

    private void setUpSpinner() {
        SemesterSpinnerValueFactory valueFactory = new SemesterSpinnerValueFactory(study);
        valueFactory.setLowestPossibleValue();
        semesterChooserSpinner.setValueFactory(valueFactory);
    }
}
