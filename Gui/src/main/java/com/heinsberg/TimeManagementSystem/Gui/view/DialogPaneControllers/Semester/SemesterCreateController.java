package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Semester;

import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SemesterCreateController extends SemesterManipulateController implements Initializable {

    public SemesterCreateController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName, Study study) {
        super(contentManager, viewFactory, fxmlName, study);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpBasicFunctions();
        setUpPreDefinedSpinnerValue();
    }

    /**
     * sets the Spinner Value to the lowest possible Semester
     */
    private void setUpPreDefinedSpinnerValue() {
        semesterViewFactory.increment(1);
    }

    /**
     * Creates a new Semester with the given Information and returns it
     *
     * @return
     */
    public Semester getCreatedSemester() {
        Semester outPut = new Semester(semesterChooser.getValue(), null, null, null);
        return outPut;
    }
}
