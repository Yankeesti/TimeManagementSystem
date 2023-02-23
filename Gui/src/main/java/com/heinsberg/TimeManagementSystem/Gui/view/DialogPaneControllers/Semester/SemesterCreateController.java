package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Semester;

import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.SpinnerViewFactorys.SpinnerSemesterCreateValueFactory;
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
     * Sets up the Spinner the predefinded Value needs to be set in the child of this class
     */
    protected void setUpSpinner() {
        semesterViewFactory = new SpinnerSemesterCreateValueFactory(study);
        semesterChooser.setValueFactory(semesterViewFactory);
        semesterViewFactory.increment(1);

        //add Listener to Value Spinner
        semesterViewFactory.valueProperty().addListener(e -> {
            upDatePossibleDates();
            upDateStartDatePicker();
            upDateEndDatePicker();
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
        });
    }
}
