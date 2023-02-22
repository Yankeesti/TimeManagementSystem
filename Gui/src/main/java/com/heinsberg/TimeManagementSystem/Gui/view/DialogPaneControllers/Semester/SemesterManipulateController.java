package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Semester;

import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.BaseController;
import com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.SpinnerViewFactorys.SpinnerSemesterViewFactory;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;

public abstract class SemesterManipulateController extends BaseController {
    @FXML
    protected Spinner<Integer> semesterChooser;

    @FXML
    protected DatePicker endDatePicker;

    @FXML
    protected DatePicker startDatePicker;

    protected SpinnerSemesterViewFactory semesterViewFactory;

    protected Study study;

    public SemesterManipulateController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName, Study study) {
        super(contentManager, viewFactory, fxmlName);
        this.study = study;
    }

    protected void setUpBasicFunctions(){setUpSpinner();}


    /**
     * Sets up the Spinner the predefinded Value needs to be set in the child of this class
     */
    private void setUpSpinner() {
        semesterViewFactory = new SpinnerSemesterViewFactory(study);
        semesterChooser.setValueFactory(semesterViewFactory);
    }


}
