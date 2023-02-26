package com.heinsberg.TimeManagementSystem.BackGround.Project;

import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.TimeManagementSystem.BackGround.WeekFactory;
import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.Week;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.ChangeEnums.SubjectChange;
import javafx.collections.ListChangeListener;

import java.util.Date;

/**
 * A Class To Store Information about a Project and what was learned during this Project
 */
public class Project extends TimeSpentContainer {
    int learnedInCurrentWeek = 0; //in Minutes
    private Week currentWeek;

    public Project(String name, WeekFactory weekFactory) {
        super(name,weekFactory);
    }

    public Project(String name){
        super(name);
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
                learnedInCurrentWeek += learningPhase.getDifference() / 1000;
            }
        }
        learnedInCurrentWeek /= 60;
    }

    public void editInformation(String newName, int newWeekGoal) {
        name = newName;
        weekGoal = newWeekGoal;
        notifyListeners(SubjectChange.EDITED_SUBJECT);
    }
}
