package com.heinsberg.LearningManagerProjekt.BackGround.subject;

import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Week;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;

import java.util.ArrayList;

/**
 * Represents a subject in a study program.
 *
 * @author Heinsberg
 */
public class Subject {
    private double finalGrade;
    private int ectsPoints;
    private int weekGoal;//learning goal per week in Minutes
    private String subjectName;
    private ArrayList<LearningPhase> learningPhases;
    private int semester;
    private Week currenWeek;

    /**
     * Creates a new Subject with given name, semester and ects points.
     *
     * @param subjectName - The name of the subject.
     * @param semester    - The semester in which the subject is taken.
     * @param ectsPoints  - The number of ECTS points for this subject.
     */

    public Subject(String subjectName, int semester, int ectsPoints) {
        learningPhases = new ArrayList<LearningPhase>();
        this.subjectName = subjectName;
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

    /**
     * Returns the semester in which this subject is taken.
     *
     * @return The semester in which this subject is taken.
     */
    public int getSemester() {
        return semester;
    }
}
