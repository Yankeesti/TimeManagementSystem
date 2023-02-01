package com.heinsberg.LearningManager.Gui.controller.componentController;

import com.heinsberg.LearningManager.Gui.ContentManager;
import com.heinsberg.LearningManager.Gui.controller.componentController.TimeSpentContainterController.BaseTimeSpentContainerController;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Project.Project;
import com.heinsberg.LearningManagerProjekt.BackGround.study.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.LearningManagerProjekt.BackGround.study.subject.Subject;

public class ProjectInformationController extends BaseTimeSpentContainerController {
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
}
