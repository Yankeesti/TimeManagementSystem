package com.heinsberg.LearningManagerProjekt.BackGround.subject;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Week;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a subject in a study program.
 *
 * @author Heinsberg
 */
public class Subject {
    private double finalGrade;
    private int ectsPoints;



    private int weekGoal; //learning goal per week in Minutes
    private String subjectName;
    private SimpleStringProperty subjectNameProperty;
    private ArrayList<LearningPhase> learningPhases;
    private Semester semester;
    private Week currenWeek;

    /**
     * Creates a new Subject with given name, semester and ects points.
     *
     * @param subjectName - The name of the subject.
     * @param semester    - The semester in which the subject is taken.
     * @param ectsPoints  - The number of ECTS points for this subject.
     */

    public Subject(String subjectName, Semester semester, int ectsPoints) {
        learningPhases = new ArrayList<LearningPhase>();
        this.subjectName = subjectName;
        this.subjectNameProperty = new SimpleStringProperty(subjectName);
        this.semester = semester;
        this.ectsPoints = ectsPoints;
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

    public void setWeekGoal(int weekGoal) {
        this.weekGoal = weekGoal;
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

    public ArrayList<LearningPhase> getLearningPhases() {
        return learningPhases;
    }

    public Week getCurrenWeek() {
        return currenWeek;
    }

    public LearningPhase startLearningPhase() {
        LearningPhase learningPhase = new LearningPhase(this);
        return learningPhase;
    }


    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    @Override
    public String toString(){
        return subjectName;
    }
}
