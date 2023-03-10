package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Semester;

import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.BaseController;
import com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.SpinnerViewFactorys.SpinnerSemesterCreateValueFactory;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.ZoneId;

public abstract class SemesterManipulateController extends BaseController {
    @FXML
    protected Spinner<Integer> semesterChooser;

    @FXML
    protected DatePicker endDatePicker;

    @FXML
    protected DatePicker startDatePicker;
    @FXML
    protected DialogPane dialogPane;
    @FXML
    protected Label errorLabel;

    protected SpinnerValueFactory.IntegerSpinnerValueFactory semesterViewFactory;

    protected Study study;

    protected Button okButton;

    protected LocalDate[] possibleDates;//Date Time Span that is possible to choose in Date Pickers [0] start of timeSpan [1] 0 end of Time Span

    public SemesterManipulateController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName, Study study) {
        super(contentManager, viewFactory, fxmlName);
        this.study = study;
    }

    protected void setUpBasicFunctions() {
        setUpSpinner();
        upDatePossibleDates();
        upDateEndDatePicker();
        upDateStartDatePicker();
        setUpDatePickerListener();
        setUpOkButton();
    }

    private void setUpOkButton() {
        okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
    }

    /**
     * Updates the Possible Date to choose in Date Picker
     */
    protected void upDatePossibleDates() {
        Semester[] surroundingSemesters = study.getSurroundingSemesters(semesterChooser.getValue());
        possibleDates = new LocalDate[2]; //holds the start and the end of all possible Dates

        if (surroundingSemesters[0] != null) {
            possibleDates[0] = surroundingSemesters[0].getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else {
            possibleDates[0] = LocalDate.MIN;
        }

        if (surroundingSemesters[1] != null) {
            possibleDates[1] = surroundingSemesters[1].toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else {
            possibleDates[1] = LocalDate.MAX;
        }
    }

    private void setUpDatePickerListener() {
        startDatePicker.valueProperty().addListener(e -> {
            upDateEndDatePicker();
            if(startDatePicker.getValue() != null && endDatePicker.getValue() != null){
                okButton.setDisable(false);
            }else{
                okButton.setDisable(true);
            }
        });
        endDatePicker.valueProperty().addListener(e -> {
            upDateStartDatePicker();
            if(startDatePicker.getValue() != null && endDatePicker.getValue() != null){
                okButton.setDisable(false);
            }else{
                okButton.setDisable(true);
            }
        });
    }


    /**
     * Sets up the Spinner the predefinded Value needs to be set in the child of this class
     */
    protected abstract void setUpSpinner();


    /**
     * Updates the End Date Pickers selectable Dates
     */
    protected void upDateEndDatePicker() {

        endDatePicker.dayCellFactoryProperty().set(datePicker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (startDatePicker.getValue() != null) {
                    if (startDatePicker.getValue().isAfter(date))
                        setDisable(true);//start Date would be before end Date
                }
                if (!(possibleDates[0].isBefore(date) && possibleDates[1].isAfter(date)))
                    setDisable(true);
            }
        });
    }

    /**
     * Updates the Start Date Pickers selectable Dates
     */
    protected void upDateStartDatePicker() {
        startDatePicker.dayCellFactoryProperty().set(datePicker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (endDatePicker.getValue() != null) {
                    if (endDatePicker.getValue().isBefore(date))
                        setDisable(true);//start Date would be before end Date
                }
                if (!(possibleDates[0].isBefore(date) && possibleDates[1].isAfter(date)))
                    setDisable(true);
            }
        });
    }


}
