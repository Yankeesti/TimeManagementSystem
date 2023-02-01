package com.heinsberg.LearningManagerProjekt.BackGround.abstractClasses;

import com.heinsberg.LearningManagerProjekt.BackGround.study.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.LearningManagerProjekt.BackGround.study.Listeners.SubjectListener;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Week;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Class is used to symbolise the base of a Object that can contain LearnPhases
 * Classes that inherite from ist are Subject and Projekt, a Subject need to have a Semester where it is in because it is in a study
 * while a Projekt dosen't need a Study and can be used alone
 *
 * This Class handles the Basic information for a Project/Subject
 */
public abstract class TimeSpentContainer {
    protected int weekGoal; //learning goal per week in Minutes when -1 o e the Object dosent have a week Goal
    protected String name; //name of the Object
    protected ObservableList<LearningPhase> learningPhases;//learningPhases/Time spent for the Projekt
    protected Week currentWeek;
    protected ArrayList<SubjectListener> listeners = new ArrayList<SubjectListener>();

    public TimeSpentContainer(String name) {
        this.name = name;
        learningPhases = FXCollections.observableArrayList();
    }

    /**
     * Method to start A learnongPhase inside of the Project/Subject
     * @return
     */
    protected LearningPhase startLearningPhaseIntern() {
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



//Getters And Setters
    //Getters
    public int getWeekGoal() {
        return weekGoal;
    }

    /**
     * @return - the Name of the Object
     */
    public String getName(){
        return name;
    }

    public ObservableList<LearningPhase> getLearningPhases() {
        return learningPhases;
    }
    //Setters
    public void setName(String newName){
        this.name = newName;
        notifyListeners(SubjectChange.CHANGED_NAME);
    }

    public void setWeekGoal(int weekGoal){
        this.weekGoal = weekGoal;
        notifyListeners(SubjectChange.CHANGED_WEEK_GOAL);
    }

    //Methods for Listners
    /**
     * This method is used to notify all registered listeners that a change has occurred in the subject.
     * The change is passed as a parameter of type SubjectChange.
     * This method starts a new thread to run the notification process, allowing the rest of the program to continue executing while the listeners are being notified.
     *
     * @param subjectChange The change that has occurred in the subject.
     */

    protected void notifyListeners(SubjectChange subjectChange) {
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

    @Override
    public String toString(){return name;}
}
