package com.heinsberg.LearningManager.Gui.view.DialogPaneControllers.Project;

import com.heinsberg.LearningManager.Gui.ContentManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.view.DialogPaneControllers.SpinnerViewFactorys.SpinnerMinuteViewFactory;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Project.Project;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class ProjectManipulateController extends BaseController{

    @FXML
    protected DialogPane dialogPane;

    @FXML
    protected Spinner<Integer> hourChooser;

    @FXML
    protected Spinner<Integer> minuteChooser;

    @FXML
    protected TextField projectNameField;

    public ProjectManipulateController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    /**
     * Sets up the Basic Functions for a Project Dialog Window
     */
    protected void setUpBasicFunctions(){
        setUpSpinner();
        setUpNameField();
    }


    private void setUpNameField() {
        projectNameField.setPromptText("ProjectName");
        checkInput();
        projectNameField.textProperty().addListener((observable) -> {
            checkInput(); //when text is edited Check input
        });
    }

    private void checkInput() {
        Button okButton = okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        if (!projectNameField.getText().isEmpty()) {
            okButton.setDisable(false);
        } else {
            okButton.setDisable(true);
        }
    }

    private void setUpSpinner() {
        SpinnerValueFactory.IntegerSpinnerValueFactory hourViewFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE);
        hourChooser.setValueFactory(hourViewFactory);

        SpinnerMinuteViewFactory minuteViewFactory = new SpinnerMinuteViewFactory(hourViewFactory);
        minuteChooser.setValueFactory(minuteViewFactory);
    }

    /**
     * Calculates the choosen week goal in Minutes
     * @return chosen Week Goal in Minutes
     */
    protected int getWeekGoal(){
        return hourChooser.getValue()*60+minuteChooser.getValue();
    }
}
