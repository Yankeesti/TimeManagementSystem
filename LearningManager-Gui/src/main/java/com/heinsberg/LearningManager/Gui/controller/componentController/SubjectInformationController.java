package com.heinsberg.LearningManager.Gui.controller.componentController;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.LearningManagerProjekt.BackGround.Listeners.SubjectListener;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;

public class SubjectInformationController extends BaseInformationComponentController {
    @FXML
    private Label titleLabel;
    @FXML
    private Label weekGoalLabel,learnedLabel;
    @FXML
    private TableView learningPhaseView;
    private Subject subject;

    public SubjectInformationController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName) {
        super(studyManager, viewFactory, fxmlName);
    }

    @FXML
    void subjectSettingsAction() {
        viewFactory.getDialogViewFactory().showSubjectEditor(subject);
    }

    @Override
    public void upDateInformation(Object subject) throws ClassCastException {
        if (subject.getClass() == Subject.class) {
            this.subject = (Subject) subject; //updates the Subject
            this.titleLabel.setText(this.subject.getSubjectName());//updates Title
            setWeekGoalLabel();

            ((Subject) subject).addListener(new SubjectListener() {//when Something changes in Subject the information gets Updated
                @Override
                public void changed(SubjectChange subjectChange) {
                    Platform.runLater(() -> {//lets Actions in Lambda Expression run on javaFx Thread
                        upDateInformation(subject);
                    });
                }
            });
        } else {
            throw new ClassCastException("Object must be from type Subject");
        }
    }

    private void setWeekGoalLabel(){
        int weekGoal = ((Subject) subject).getWeekGoal();
        if(weekGoal > 0 )//when weekGoal is set show goal
        {
            int hours = weekGoal /60;
            int minutes = weekGoal%60;
            weekGoalLabel.setText(hours+" Stunden und "+minutes+" Minuten ");
            weekGoalLabel.setStyle("-fx-background-color: transparent;");

        }else{
            weekGoalLabel.setStyle("-fx-background-color: red;");
            weekGoalLabel.setText("Es wurde noch kein Wochen Ziel Festgelegt");
        }
    }

}
