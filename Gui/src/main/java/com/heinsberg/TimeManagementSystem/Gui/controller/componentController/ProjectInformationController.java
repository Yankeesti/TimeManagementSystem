package com.heinsberg.TimeManagementSystem.Gui.controller.componentController;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.TimeSpentContainterController.BaseTimeSpentContainerController;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import com.heinsberg.TimeManagementSystem.BackGround.Project.Project;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectInformationController extends BaseTimeSpentContainerController implements Initializable {
    public ProjectInformationController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }


    @Override
    protected void showChanges(SubjectChange subjectChange) {
        switch (subjectChange){
            case EDITED_SUBJECT:
                upDateInformation(shownObject);
                break;
        }
    }

    @Override
    protected void TimeSpentContainerSettingsActionCalled() {
        viewFactory.getDialogViewFactory().showProjectEditor((Project) shownObject);
    }

    @Override
    protected void setUpTimeProgressInformation() {
        if (((Project) shownObject).getWeekGoal() > 0) {
            learnedInformationPane.setVisible(true);
            learnedInformationPane.setManaged(true);
            setUpLearned();
            setWeekGoalLable();
        } else {
            learnedInformationPane.setVisible(false);
            learnedInformationPane.setManaged(false);
        }
    }

    private void setUpLearned() {
        int learned = ((Project) shownObject).getLearnedInCurrentWeek();
        if (learned == 0) { //when it was not learned for subject yer
            learnedLabel.setText("Diese Woche wurde noch nicht and " + (shownObject.getName()+ " gearbeitet"));
        } else {
            int[] learnedFormated = getInHoursAndMinutes(learned);
            if (learnedFormated[0] == 0) {
                learnedLabel.setText("Diese woche wurde " + learnedFormated[1] + " Minuten and " + ((Subject) shownObject).getSubjectName() + " gearbeitet");
            }else
            learnedLabel.setText("Diese woche wurde " + learnedFormated[0] + " Stunden und" + learnedFormated[1] + " Minuten an " + ((Subject) shownObject).getSubjectName() + " gearbeitet");
        }
    }

    /**
     * Method is called when ProjectInformation Controller is created,
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpBase();
    }
}
