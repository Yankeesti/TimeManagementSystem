package com.heinsberg.LearningManager.Gui.controller.componentController;

import com.heinsberg.LearningManager.Gui.ContentManager;
import com.heinsberg.LearningManager.Gui.controller.componentController.TimeSpentContainterController.BaseTimeSpentContainerController;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.study.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.LearningManagerProjekt.BackGround.study.subject.Subject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SubjectInformationController extends BaseTimeSpentContainerController implements Initializable {



    public SubjectInformationController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    @Override
    protected void showChanges(SubjectChange subjectChange) {
        upDateInformation(shownObject);
    }


    @Override
    protected void TimeSpentContainerSettingsActionCalled() {
        viewFactory.getDialogViewFactory().showSubjectEditor((Subject) shownObject);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpBase();
    }

    protected void setUpTimeProgressInformation(){
        if (((Subject) shownObject).gradeIsSet()) { // if grade is set it's not necessery to show learned Information
            learnedInformationPane.setVisible(false);
            learnedInformationPane.setManaged(false);
        }else{
            learnedInformationPane.setVisible(true);
            learnedInformationPane.setManaged(true);
            setUpLearned();
            setWeekGoalLable();
        }
    }


    protected void setUpLearned() {
        int learned = contentManager.getStudy().getLearnedInCurrentWeek((Subject) shownObject);
        if (learned < 0) {
            learnedLabel.setText("Dieses Fach ist nicht im aktuellen Semster");
            weekGoalLabel.setStyle("-fx-background-color: red;");
        } else {
            if (learned == 0) { //when it was not learned for subject yer
                learnedLabel.setText("Diese Woche wurde noch nicht für " + ((Subject) shownObject).getSubjectName() + " gelernt");
            } else {
                int[] learnedFormated = getInHoursAndMinutes(learned);
                if (learnedFormated[0] == 0) {
                    learnedLabel.setText("Diese woche wurde " + learnedFormated[1] + " Minuten für " + ((Subject) shownObject).getSubjectName() + " gelernt");
                }else
                learnedLabel.setText("Diese woche wurde " + learnedFormated[0] + " Stunden und" + learnedFormated[1] + " Minuten für " + ((Subject) shownObject).getSubjectName() + " gelernt");
            }
            learnedLabel.setStyle("-fx-background-color: transparent;");
        }
    }


}
