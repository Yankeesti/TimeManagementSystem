package com.heinsberg.LearningManager.Gui.view.DialogPaneControllers.Subject;

import com.heinsberg.LearningManager.Gui.ContentManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.view.DialogPaneControllers.SpinnerViewFactorys.SpinnerMinuteViewFactory;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * The SubjectManipulateBaseController class serves as a base class for subject manipulation dialogs.
 * It provides the basic functionality for the subject manipulation dialog, such as setting up spinners for hours, minutes, ECTS points and grades.
 * It also handles the logic for setting up check boxes for grading and currently ungraded.
 * The class is designed to be extended by other controllers that require this functionality.
 * It is a subclass of the BaseController class.
 */
public abstract class SubjectManipulateBaseController extends BaseController {

    @FXML
    protected Spinner<Integer> ectsChooser;

    @FXML
    protected Spinner<Integer> hourChooser;

    @FXML
    protected Spinner<Integer> minuteChooser;

    @FXML
    protected TextField subjectNameField;
    @FXML
    protected CheckBox ungradedCheckBox, currentlyUngradedCheckBox;
    @FXML
    protected Spinner<Double> gradeSpinner;
    @FXML
    protected DialogPane dialogPane;

    protected SpinnerValueFactory.IntegerSpinnerValueFactory ectsValueFactory, hourValueFactory;
    protected SpinnerValueFactory<Integer> minuteValueFactory;
    protected SpinnerValueFactory.DoubleSpinnerValueFactory gradSpinnerValueFactory;

    public SubjectManipulateBaseController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    /**
     * Sets up the Basic propertys of a SubjectManipulate Controller like
     * spinners and the grade cheackbox
     */
    protected void setUpBasic() {
        setUpSpinners();
        setUpGradeCheckBoxes();

    }

    /**
     * Sets up the Checkboxes and the Logic behind them
     */
    private void setUpGradeCheckBoxes() {
        ungradedCheckBox.setOnAction((evt) -> {
            if (ungradedCheckBox.isSelected()) {//checkBox is selected make gradeSpinner unclickable
                currentlyUngradedCheckBox.setSelected(false);
                gradeSpinner.setDisable(true);
            } else {
                gradeSpinner.setDisable(false);
            }
        });
        currentlyUngradedCheckBox.setOnAction((evt) -> {
            if (currentlyUngradedCheckBox.isSelected()) {//when checked disable gradeSpinner and ungraded Checkbox
                ungradedCheckBox.setSelected(false);
                gradeSpinner.setDisable(true);
            } else {
                if (ungradedCheckBox.isSelected()) {// when ungradedCheckbox is selected enable ungradedCheckbox
                    gradeSpinner.setDisable(true);
                    ungradedCheckBox.setSelected(false);
                } else {
                    gradeSpinner.setDisable(false);
                }
            }
        });
    }

    /**
     * Sets up the Spinners for the Dialog Box, but doesnt give them pre defined Values
     */
    private void setUpSpinners() {
        //SetUp EctsPoint Spinner
        ectsValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20);
        ectsChooser.setValueFactory(ectsValueFactory);

        //setUp hourChooser
        hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 70);
        hourChooser.setValueFactory(hourValueFactory);

        //setUp minuteChooser
        minuteValueFactory = new SpinnerMinuteViewFactory(hourValueFactory);
        minuteChooser.setValueFactory(minuteValueFactory);

        //Set up Grade Spinner
        gradSpinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 6);
        gradSpinnerValueFactory.setAmountToStepBy(0.1);
        gradeSpinner.setValueFactory(gradSpinnerValueFactory);
    }
}
