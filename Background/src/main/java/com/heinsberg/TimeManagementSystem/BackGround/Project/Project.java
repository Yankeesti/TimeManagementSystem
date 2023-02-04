package com.heinsberg.TimeManagementSystem.BackGround.Project;

import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.TimeManagementSystem.BackGround.WeekFactory;
import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.Week;
import com.heinsberg.TimeManagementSystem.BackGround.LearningPhaseActionResult;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.ChangeEnums.SubjectChange;
import javafx.collections.ListChangeListener;

import java.util.Date;

/**
 * A Class To Store Information about a Project and what was learned during this Project
 */
public class Project extends TimeSpentContainer {
    int learnedInCurrentWeek = 0; //in Minutes
    private Week currentWeek;

    private LearningPhase currentLearningPhase;

    public Project(String name, WeekFactory weekFactory) {
        super(name,weekFactory);
    }

    public LearningPhaseActionResult startLearningPhase(){
        currentWeek = weekFactory.getCurrentWeek();
        if(currentLearningPhase == null){//no learningPhase was started yet
            currentLearningPhase = super.startLearningPhaseIntern();
            return LearningPhaseActionResult.SUCCESS;
        }else{
            return  LearningPhaseActionResult.LEARNINGPHASE_ALREADY_STARTED;
        }
    }

    public LearningPhaseActionResult finishLearningPhase(){
        if(currentLearningPhase != null){
            currentLearningPhase.endLearningPhase();
            currentLearningPhase = null;
            return LearningPhaseActionResult.SUCCESS;
        }else{
            return LearningPhaseActionResult.NO_CURRENTLEARNINGPHASE;
        }
    }

    /**
     * Adds a Listener to learningPhases
     * when a learningPhase is Added learnedInCurrentWeek is automaticly reclculated
     */
    private void setUpLearningPhaseListener() {
        learningPhases.addListener(new ListChangeListener<LearningPhase>() {
            @Override
            public void onChanged(Change<? extends LearningPhase> change) {
                change.next();
                if (change.wasAdded()) {
                    calculateLearned();
                } else {
                    if (change.wasRemoved())
                        calculateLearned();
                }
            }
        });
    }

    /**
     * Calculates how much was learned in the Current Week
     */
    private void calculateLearned() {
        if (currentWeek != null) {
            Date aktDate = currentWeek.getAktDate();
            if (currentWeek.compareTo(aktDate) != 0) {//currentDate is not in the currentWeek
                currentWeek = new Week(aktDate);
            }
        } else {
            currentWeek = new Week(Week.getAktDate());
        }
        learnedInCurrentWeek = 0;
        for (LearningPhase learningPhase : learningPhases) {
            if (currentWeek.compareTo((Date) learningPhase) == 0) {
                learnedInCurrentWeek += learningPhase.getDiffrence() / 1000;
            }
        }
        learnedInCurrentWeek /= 60;
    }

    public void editInformation(String newName, int newWeekGoal) {
        name = newName;
        weekGoal = weekGoal;
        notifyListeners(SubjectChange.EDITED_SUBJECT);
    }
}
