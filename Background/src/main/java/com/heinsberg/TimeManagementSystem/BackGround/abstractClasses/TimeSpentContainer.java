package com.heinsberg.TimeManagementSystem.BackGround.abstractClasses;

import com.heinsberg.TimeManagementSystem.BackGround.WeekFactory;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.SubjectListener;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.Week;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;

/**
 * Class is used to symbolise the base of a Object that can contain LearnPhases
 * Classes that inherite from ist are Subject and Projekt, a Subject need to have a Semester where it is in because it is in a study
 * while a Projekt dosen't need a Study and can be used alone
 * <p>
 * This Class handles the Basic information for a Project/Subject
 */
public abstract class TimeSpentContainer {
    protected int weekGoal; //learning goal per week in Minutes when -1 o e the Object dosent have a week Goal
    protected String name; //name of the Object
    protected ObservableList<LearningPhase> learningPhases;//learningPhases/Time spent for the Projekt
    protected Week currentWeek;
    protected ArrayList<SubjectListener> listeners = new ArrayList<SubjectListener>();

    protected WeekFactory weekFactory;

    public TimeSpentContainer(String name, WeekFactory weekFactory) {
        this.name = name;
        learningPhases = FXCollections.observableArrayList();
        this.weekFactory = weekFactory;
    }


    /**
     * Constructor only used for loading from Json File, when used WeekFactory needs to be Set after loading
     *
     * @param name
     */
    public TimeSpentContainer(String name) {
        this.name = name;
        learningPhases = FXCollections.observableArrayList();
    }

    /**
     * used when loaded from a Json file
     * sets the weekFactory and loads all LearningPhases in to the Weeks they belong to
     *
     * @param weekFactory
     */
    public void setWeekFactory(WeekFactory weekFactory) {
        if (this.weekFactory == null) {
            this.weekFactory = weekFactory;
            //load all LearningPhases in to Weeks
            for (LearningPhase learningPhase : learningPhases) {
                weekFactory.getWeek(learningPhase).addLearningPhase(learningPhase);
            }
        } else
            System.err.println("Weekfactory is already set TSC");

    }

    /**
     * Method to start A learnongPhase inside of the Project/Subject and adds it to the Week the LearningPhase belongs in
     *
     * @return
     */
    public LearningPhase startLearningPhase() {
        LearningPhase learningPhase = new LearningPhase(this);
        learningPhases.add(learningPhase);
        weekFactory.getWeek(learningPhase).addLearningPhase(learningPhase); // add LearningPhase to Week
        return learningPhase;
    }

    /**
     * Adds a LearningPhase to this subject.
     *
     * @param learningPhase - The LearningPhase to be added to this subject.
     */
    public void addLearningPhase(LearningPhase learningPhase) {
        if (!learningPhases.contains(learningPhase))
            learningPhases.add(learningPhase);
        weekFactory.getWeek(learningPhase).addLearningPhase(learningPhase);
    }

    public int getLearnedInCurrentWeek() {
        return weekFactory.getCurrentWeek().getLearnedFor(this);
    }


    //Getters And Setters
    //Getters
    public int getWeekGoal() {
        return weekGoal;
    }

    /**
     * @return - the Name of the Object
     */
    public String getName() {
        return name;
    }

    public ObservableList<LearningPhase> getLearningPhases() {
        return learningPhases;
    }

    //Setters
    public void setName(String newName) {
        this.name = newName;
        notifyListeners(SubjectChange.CHANGED_NAME);
    }

    public void setWeekGoal(int weekGoal) {
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
     * deletes the given LearningPhase in Subject/Project and the week it is in
     *
     * @param learningPhase
     */
    public void deleteLearningPhase(LearningPhase learningPhase) {
        learningPhases.remove(learningPhase);
        weekFactory.getWeek(learningPhase).removeLearningPhase(learningPhase);
    }

    public boolean equals(TimeSpentContainer timeSpentContainer) {
        if (weekGoal != timeSpentContainer.getWeekGoal())
            return false;
        if (!name.equals(timeSpentContainer.getName()))
            return false;

        timeSpentContainer.sortLearningPhases();
        sortLearningPhases();
        ObservableList<LearningPhase> l2 = timeSpentContainer.learningPhases;
        if (l2.size() != learningPhases.size())
            return false;

        for (int i = 0; i < l2.size(); i++) {
            if (!learningPhases.get(i).equals(l2.get(i)))
                return false;
        }
        return true;
    }

    public void sortLearningPhases() {
        learningPhases.sort(((o1, o2) -> {
            return ((Date) o1).compareTo(((Date) o2));
        }));
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * deletes all LearningPhases that belong to this TimeSpentConainer
     * also deletes the LearningPhases out of the Week they belong to
     */
    public void deleteLearningPhases() {
        for (LearningPhase learningPhase : learningPhases) {
            weekFactory.getWeek(learningPhase).removeLearningPhase(learningPhase);
        }
    }
}
