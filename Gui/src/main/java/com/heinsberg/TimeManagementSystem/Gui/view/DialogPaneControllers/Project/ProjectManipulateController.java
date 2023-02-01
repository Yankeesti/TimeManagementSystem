package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Project;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.BaseController;
import com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.SpinnerViewFactorys.SpinnerMinuteViewFactory;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public abstract class ProjectManipulateController extends BaseController{

    @FXML
    protected DialogPane dialogPane;

    @FXML
    protected Spinner<Integer> hourChooser;

    @FXML
    protected Spinner<Integer> minuteChooser;

    @FXML
    protected TextField projectNameField;
    protected SpinnerValueFactory.IntegerSpinnerValueFactory hourViewFactory;
    protected SpinnerMinuteViewFactory minuteViewFactory;

    public ProjectManipulateController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    /**
     * Sets up the Basic Functions for a Project Dialog Window
     */
    protected void setUpBasicFunctions(){
        setUpSpinner();
    }
    private void setUpSpinner() {
        hourViewFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE);
        hourChooser.setValueFactory(hourViewFactory);

        minuteViewFactory = new SpinnerMinuteViewFactory(hourViewFactory);
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
