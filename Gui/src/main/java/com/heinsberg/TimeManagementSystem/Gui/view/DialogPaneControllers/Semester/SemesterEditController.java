package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Semester;

import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.SpinnerViewFactorys.SpinnerSemesterEditValueFactory;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class SemesterEditController extends SemesterManipulateController implements Initializable {

    private Semester semesterToEdit;
    public SemesterEditController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName, Semester semesterToEdit) {
        super(contentManager, viewFactory, fxmlName, semesterToEdit.getStudy());
        this.semesterToEdit = semesterToEdit;
    }

    /**
     * Saves the changes to the Semester
     */
    public void submitChanges(){
        semesterToEdit.getStudy().submitChanges(semesterToEdit, semesterChooser.getValue(),Date.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),Date.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    @Override
    protected void setUpSpinner() {
        semesterViewFactory = new SpinnerSemesterEditValueFactory(semesterToEdit);
        semesterChooser.setValueFactory(semesterViewFactory);
        semesterViewFactory.setValue(semesterToEdit.getSemester());

        //add Listener to Value Spinner
        semesterViewFactory.valueProperty().addListener(e -> {
            upDatePossibleDates();
            upDateStartDatePicker();
            upDateEndDatePicker();
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpBasicFunctions();
        setUpOkButton();
        setUpPreDefinedDates();
    }

    /**
     * Sets the pre definded Dates to the Start and end Date of Semester
     */
    private void setUpPreDefinedDates() {
        startDatePicker.setValue(semesterToEdit.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        endDatePicker.setValue(semesterToEdit.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    private void setUpOkButton(){
        okButton.setText("Speicher");
    }
}
