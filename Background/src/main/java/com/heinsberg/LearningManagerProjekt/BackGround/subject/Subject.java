package com.heinsberg.LearningManagerProjekt.BackGround.subject;

import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Week;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;

import java.util.ArrayList;

public class Subject {
    private double finalGrade;
    private int ectsPoints;
    private int weekGoal;//learning goal per week in Minutes
    private String subjectName;
    private ArrayList<LearningPhase> learningPhases;
    private int semester;
    private Week currenWeek;

    public Subject(String subjectName, int semester, int ectsPoints) {
        learningPhases = new ArrayList<LearningPhase>();
        this.subjectName = subjectName;
        this.semester = semester;
        this.ectsPoints = ectsPoints;
    }


    public void addLearningPhase(LearningPhase learningPhase) {
        learningPhases.add(learningPhase);
    }

    //Getter and Setter
    public void setFinalGrade(double finalGrade) {
        this.finalGrade = finalGrade;
    }

    public void setWeekGoal(int weekGoal) {
        this.weekGoal = weekGoal;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSemester(int semester) {
        this.semester = semester;
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

    public String getSubjectName() {
        return subjectName;
    }

    public int getSemester() {
        return semester;
    }
}
