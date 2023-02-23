package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Semester;

import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SemesterCreateController extends SemesterManipulateController implements Initializable {


    public SemesterCreateController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName, Study study) {
        super(contentManager, viewFactory, fxmlName, study);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpBasicFunctions();
        setUpOkButton();

    }


    /**
     * Changes the text of Ok Button to Erstellen and disables it
     */
    private void setUpOkButton() {
        okButton.setDisable(true);
        okButton.setText("Erstellen");
    }

    /**
     * Creates a new Semester with the given Information and returns it
     *
     * @return
     */
    public Semester getCreatedSemester() {
        Semester outPut = new Semester(semesterChooser.getValue(), java.sql.Date.valueOf(startDatePicker.getValue()), java.sql.Date.valueOf(endDatePicker.getValue()));
        return outPut;
    }

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
