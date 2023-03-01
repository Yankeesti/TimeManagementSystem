package com.heinsberg.TimeManagementSystem.BackGround.study.subject;

import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.TimeManagementSystem.BackGround.WeekFactory;
import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import javafx.collections.ListChangeListener;

/**
 * Represents a subject in a study program.
 *
 * @author Heinsberg
 */
public class Subject extends TimeSpentContainer {
    private double finalGrade; //Double maxValue when ungraded and 0 when not graded yet
    private int ectsPoints;
    private Semester semester;
    private Study study;

    /**
     * Creates a new Subject with given name, semester and ects points.
     *
     * @param subjectName - The name of the subject.
     * @param semester    - The semester in which the subject is taken.
     * @param ectsPoints  - The number of ECTS points for this subject.
     */

    public Subject(String subjectName, Semester semester, int ectsPoints, WeekFactory weekFactory) {
        super(subjectName, weekFactory);
        this.semester = semester;
        this.ectsPoints = ectsPoints;
    }

    public Subject(String subjectName, Semester semester, int ectsPoints) {
        super(subjectName);
        this.semester = semester;
        this.ectsPoints = ectsPoints;
    }

    @Override
    public LearningPhase startLearningPhase() {
        LearningPhase outPut = super.startLearningPhase();
        semester.addNewLearningPhase(outPut);
        return outPut;
    }

//Getter and Setter

    public Study getStudy() {
        return study;
    }

    /**
     * Returns the name of this subject.
     *
     * @return The name of this subject.
     */
    public String getSubjectName() {
        return name;
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

    public void setSemester(Semester semester) {
        this.semester = semester;
        notifyListeners(SubjectChange.CHANGED_SEMESTER);
    }

    public void setFinalGrade(double finalGrade){
        this.finalGrade = finalGrade;
        notifyListeners(SubjectChange.CHANGED_FINAL_GRADE);
    }

    public Boolean gradeIsSet(){
        return finalGrade!=0;
    }

    public void setEctsPoint(int ectsPoints) {
        this.ectsPoints = ectsPoints;
        notifyListeners(SubjectChange.CHANGED_ECTS_POINTS);
    }

    public void editInformation(String newName, int newEctsPoints,int newWeekoalHours,int newWeekGoalMinutes, double grade){
        this.name = newName;
        ectsPoints = newEctsPoints;
        weekGoal = 0;
        weekGoal += newWeekoalHours*60;
        weekGoal += newWeekGoalMinutes;
        this.finalGrade = grade;
        notifyListeners(SubjectChange.EDITED_SUBJECT);
    }

    public void setStudy(Study study) {
        this.study = study;
    }
}
