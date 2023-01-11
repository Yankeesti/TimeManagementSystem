package com.heinsberg.LearningManagerProjekt.BackGround.subject;

import com.heinsberg.LearningManagerProjekt.BackGround.util.LearningPhase;

import java.util.ArrayList;

public class Subject {
    private double finalGrade;
    private int ectsPoints;
    private int weekGoal;//learning goal per week in Minutes
    private int learnedThisWeek;//how much was learne this Week in minutes
    private String subjectName;
    private ArrayList<LearningPhase> learningPhases;
    private int semester;

    public Subject(String subjectName, int semester, int ectsPoints){
        learningPhases = new ArrayList<LearningPhase>();
        this.subjectName = subjectName;
        this.semester = semester;
        this.ectsPoints = ectsPoints;
    }

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

    public int getLearnedThisWeek() {
        return learnedThisWeek;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getSemester() {
        return semester;
    }
}
