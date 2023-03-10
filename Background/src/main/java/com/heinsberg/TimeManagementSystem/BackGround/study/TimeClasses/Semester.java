package com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses;

import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.TimePeriod;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.Week;
import com.heinsberg.TimeManagementSystem.BackGround.WeekFactory;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.ChangeEnums.SemesterChange;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.SemesterListener;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Semester extends TimePeriod {
    int semester;
    ObservableList<Subject> subjects;



    Week weeks[];
    private WeekFactory weekFactory;
    private Study study; //The study Semester belongs to
    private ArrayList<SemesterListener> listeners;
    private ObservableList<LearningPhase> learningPhases;

    /**
     * Creates a new semester with a given start and end date, and a semester number
     *
     * @param semester      - The number of the semester (e.g. 5th semester)
     * @param semesterStart - The start date of the semester
     * @param semesterEnd   - The end date of the semester
     */
    public Semester(int semester, Date semesterStart, Date semesterEnd, WeekFactory weekFactory) {
        super(semesterStart, semesterEnd);
        listeners = new ArrayList<SemesterListener>();
        this.semester = semester;
        this.weekFactory = weekFactory;
        Date startMonday = getMonday(semesterStart);
        weeks = new Week[calculateWeekAmount()];
        for (int i = 0; i < weeks.length; i++) {
            weeks[i] = weekFactory.getWeek(startMonday);
            startMonday.setDate(startMonday.getDate() + 7);
        }
        subjects = FXCollections.observableArrayList();
        learningPhases = FXCollections.observableArrayList();
    }

    /**
     * Constructor used when loading from a Json File
     * Week Factory needs to be set through setWeekFactory Method
     *
     * @param semester
     * @param semesterStart
     * @param semesterEnd
     */
    public Semester(int semester, Date semesterStart, Date semesterEnd) {
        super(semesterStart, semesterEnd);
        this.semester = semester;
        subjects = FXCollections.observableArrayList();
        listeners = new ArrayList<SemesterListener>();
        learningPhases = FXCollections.observableArrayList();
    }
    //Control Methods

    //Getter and Setter

    /**
     * Adds a subject to the list of subjects for this semester
     *
     * @param subject - The subject to add
     */
    public void addSubject(Subject subject) {
        subjects.add(subject);
        subject.setStudy(study);
    }

    /**
     * Returns the semester number of this semester
     *
     * @return The semester number
     */
    public int getSemester() {
        return semester;
    }


    //Private Methods

    /**
     * Calculates the number of weeks in this semester
     *
     * @return The number of weeks
     */
    private int calculateWeekAmount() {
        Date startMonday = getMonday(this);
        Date endMonday = getMonday(endDate);
        int anzahl = 0;

        while (startMonday.compareTo(endMonday) <= 0) {
            startMonday.setDate(startMonday.getDate() + 7);
            anzahl++;
        }
        return anzahl;
    }

    /**
     * Returns the list of subjects for this semester
     *
     * @return Array of subjects
     */
    public ObservableList<Subject> getSubjects() {
        return subjects;
    }

    public boolean includesSubject(Subject subject) {
        return subjects.contains(subject);
    }

    /**
     * This Method is for test and loading purpos, it adds the current no matter wether the current Date is in this Semester
     *
     * @param learningPhase
     */
    public void addLearningPhaseHard(LearningPhase learningPhase) {
        if(weekFactory != null)
        weekFactory.getWeek(learningPhase).addLearningPhase(learningPhase);
        learningPhases.add(learningPhase);
    }

    /**
     * Method for loading from Data Set(Json File
     * loads the learningPhases of Subjects in to the Weeks they belong
     */
    public void loadLearningPhasesFromSubject() {
        for (Subject subject : subjects) {
            LearningPhase[] learningPhases = subject.getLearningPhases().toArray(new LearningPhase[0]);
            for (LearningPhase learningPhase : learningPhases) {
                addLearningPhaseHard(learningPhase);
            }
        }
    }

    /**
     * deletes the given LearningPhase out of it's Week and the Subject
     *
     * @param learningPhase
     */
    public void deleteLearningPhase(LearningPhase learningPhase) {
        for (Week week : weeks) {
            if (week.compareTo(learningPhase) == 0) {
                week.deleteLearningPhase(learningPhase);
                break;
            }
        }
        learningPhase.getTimeSpentContainer().deleteLearningPhase(learningPhase);
    }

    public void deleteSubject(Subject subject) {
        subjects.remove(subject);
        subject.deleteLearningPhases();
    }

    public void setWeekFactory(WeekFactory weekFactory) {
        if (weekFactory == null) {
            System.err.println("Week Factory is null");
        } else {
            this.weekFactory = weekFactory;
            Date startMonday = getMonday(this);
            weeks = new Week[calculateWeekAmount()];
            for (int i = 0; i < weeks.length; i++) {
                weeks[i] = weekFactory.getWeek(startMonday);
                startMonday.setDate(startMonday.getDate() + 7);
            }
            for (Subject subject : subjects) {
                subject.setWeekFactory(weekFactory);
            }
            weekFactory.addLearningPhases(learningPhases);
        }
    }

    public void setStudy(Study study) {
        this.study = study;
        for (Subject subject : subjects) {
            subject.setStudy(study);
        }
    }

    public Study getStudy() {
        return study;
    }
    public Week[] getWeeks() {
        return weeks;
    }

    /**
     * Deletes all Subjects of this Semester and the LearningPhases that belong to it
     */
    public void delete() {
        for (Subject subject : subjects) {//delete all LearningPhases of all subjects
            subject.deleteLearningPhases();
        }
        subjects.clear();
    }

    /**
     * Submits the changes to the Semester
     *
     * @param semester
     * @param startDate
     * @param endDate
     */
    public void submitChanges(int semester, Date startDate, Date endDate) {
        this.semester = semester;
        setTime(startDate.getTime());
        this.endDate = endDate;
        notifyListeners(SemesterChange.INFORMATION_UPDATED);
    }

    //Listener Methods
    public void addListener(SemesterListener listener) {
        listeners.add(listener);
    }

    public void removeListener(SemesterListener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners(SemesterChange change) {
        for (SemesterListener listener : listeners) {
            listener.notifyListener(change);
        }
    }

    public ObservableList<LearningPhase> getLearningPhases() {
        return learningPhases;
    }

    /**
     * Adds the new LearningPhase that was started in A Subject to the List
     *
     * @param outPut
     */
    public void addNewLearningPhase(LearningPhase outPut) {
        learningPhases.add(outPut);
    }

    public void removeLearningPhase(LearningPhase learningPhase) {
        learningPhases.remove(learningPhase);
    }
}
