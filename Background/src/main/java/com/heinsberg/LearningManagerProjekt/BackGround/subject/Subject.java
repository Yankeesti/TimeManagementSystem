package com.heinsberg.LearningManagerProjekt.BackGround.subject;

import com.heinsberg.LearningManagerProjekt.BackGround.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.LearningManagerProjekt.BackGround.Listeners.SubjectListener;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Week;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.security.Provider;
import java.util.ArrayList;

/**
 * Represents a subject in a study program.
 *
 * @author Heinsberg
 */
public class Subject {
    private double finalGrade; //Double maxValue when ungraded
    private int ectsPoints;
    private int weekGoal; //learning goal per week in Minutes
    private String subjectName;
    private ObservableList<LearningPhase> learningPhases;
    private Semester semester;
    private Week currenWeek;

    private ArrayList<SubjectListener> listeners = new ArrayList<SubjectListener>();

    /**
     * Creates a new Subject with given name, semester and ects points.
     *
     * @param subjectName - The name of the subject.
     * @param semester    - The semester in which the subject is taken.
     * @param ectsPoints  - The number of ECTS points for this subject.
     */

    public Subject(String subjectName, Semester semester, int ectsPoints) {
        learningPhases = FXCollections.observableArrayList();
        this.subjectName = subjectName;
        this.semester = semester;
        this.ectsPoints = ectsPoints;
    }

    public LearningPhase startLearningPhase() {
        LearningPhase learningPhase = new LearningPhase(this);
        learningPhases.add(learningPhase);
        return learningPhase;
    }


    /**
     * Adds a LearningPhase to this subject.
     *
     * @param learningPhase - The LearningPhase to be added to this subject.
     */
    public void addLearningPhase(LearningPhase learningPhase) {
        learningPhases.add(learningPhase);
    }

    //Getter and Setter

    /**
     * Returns the name of this subject.
     *
     * @return The name of this subject.
     */
    public String getSubjectName() {
        return subjectName;
    }


    /**
     * Returns the semester in which this subject is taken.
     *
     * @return The semester in which this subject is taken.
     */
    public Semester getSemester() {
        return semester;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public int getEctsPoints() {
        return ectsPoints;
    }

    public int getWeekGoal() {
        return weekGoal;
    }

    public ObservableList<LearningPhase> getLearningPhases() {
        return learningPhases;
    }

    public Week getCurrenWeek() {
        return currenWeek;
    }

    public void setWeekGoal(int weekGoal) {
        this.weekGoal = weekGoal;
        notifyListeners(SubjectChange.CHANGED_WEEK_GOAL);
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
        notifyListeners(SubjectChange.CHANGED_SEMESTER);
    }

    public void setFinalGrade(double finalGrade){
        this.finalGrade = finalGrade;
        notifyListeners(SubjectChange.CHANGED_FINAL_GRADE);
    }

    @Override
    public String toString() {
        return subjectName;
    }

    public void setName(String newName) {
        subjectName = newName;
        notifyListeners(SubjectChange.CHANGED_NAME);
    }

    public void setEctsPoint(int ectsPoints) {
        this.ectsPoints = ectsPoints;
        notifyListeners(SubjectChange.CHANGED_ECTS_POINTS);
    }

    /**
     * redefines the Week Goal
     *
     * @param hours   - hours to be learned per Week
     * @param minutes - Minutes to be learned per Week
     */
    public void setWeekGoal(Integer hours, Integer minutes) {
        weekGoal = 0;
        weekGoal += hours * 60;
        weekGoal += minutes;
        notifyListeners(SubjectChange.CHANGED_WEEK_GOAL);
    }

    public void editInformation(String newName, int newEctsPoints,int newWeekoalHours,int newWeekGoalMinutes, double grade){
        this.subjectName = newName;
        ectsPoints = newEctsPoints;
        weekGoal = 0;
        weekGoal += newWeekoalHours*60;
        weekGoal += newWeekGoalMinutes;
        this.finalGrade = grade;
        notifyListeners(SubjectChange.EDITED_SUBJECT);
    }

    /**
     * This method is used to notify all registered listeners that a change has occurred in the subject.
     * The change is passed as a parameter of type SubjectChange.
     * This method starts a new thread to run the notification process, allowing the rest of the program to continue executing while the listeners are being notified.
     *
     * @param subjectChange The change that has occurred in the subject.
     */

    private void notifyListeners(SubjectChange subjectChange) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (SubjectListener listener : listeners) {
                    listener.notifyListener(subjectChange);
                }
            }
        });
        thread.start();
    }

    /**
     * This method is used to register a new listener to the subject.
     * The listener will be notified when a change occurs in the subject.
     *
     * @param subjectListener The listener that should be registered to the subject.
     */
    public void addListener(SubjectListener subjectListener) {
        listeners.add(subjectListener);
    }

    /**
     * This method is used to remove a registered listener from the subject.
     * The listener will no longer be notified when a change occurs in the subject.
     *
     * @param subjectListener The listener that should be removed from the subject.
     */
    public void removeListener(SubjectListener subjectListener) {
        removeListener(subjectListener);
    }

    /**
     * deletes the given LearningPhase
     * @param learningPhase
     */
    public void deleteLearningPhase(LearningPhase learningPhase) {
        learningPhases.remove(learningPhase);
    }
}
