package com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses;

import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Semester extends TimePeriod {
    int semester;
    ArrayList<Subject> subjects;
    Week weeks[];
    private int currentWeekIndex;//current Week index when this Semester does not include the akt Date = -1

    /**
     * Creates a new semester with a given start and end date, and a semester number
     *
     * @param semester      - The number of the semester (e.g. 5th semester)
     * @param semesterStart - The start date of the semester
     * @param semesterEnd   - The end date of the semester
     */
    public Semester(int semester, Date semesterStart, Date semesterEnd) {
        super(semesterStart, semesterEnd);
        this.semester = semester;
        Date startMonday = getMonday(semesterStart);
        weeks = new Week[calculateWeekAmount()];
        for (int i = 0; i < weeks.length; i++) {
            weeks[i] = new Week(startMonday, i);
            startMonday.setDate(startMonday.getDate() + 7);
        }
        currentWeekIndex = 0;
        subjects = new ArrayList<Subject>();
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
     * Updates the current week index for this semester
     */
    private void upDateWeek() {
        Date aktDate = getAktDate();
        if (compareTo(aktDate) != 0)//Check if current Date is in this Semester
            currentWeekIndex = -1;
        for (int i = currentWeekIndex; i < weeks.length; i++) {
            if (weeks[i].compareTo(aktDate) == 0) {
                currentWeekIndex = i;
                break;
            }
        }
    }

    /**
     * Returns the list of subjects for this semester
     *
     * @return Array of subjects
     */
    public Subject[] getSubjects() {
        return subjects.toArray((new Subject[subjects.size()]));
    }
    public boolean includesSubject(Subject subject){
        return subjects.contains(subject);
    }

    public void addLearningPhase(LearningPhase currentLearningPhase) {
        upDateWeek();
        if(weeks[currentWeekIndex].compareTo(currentLearningPhase) == 0){//start of learning Phase is in Week
            weeks[currentWeekIndex].addLearningPhase(currentLearningPhase);
        }
    }

    /**
     * This Method is for test and loading purpos, it adds the current no matter wether the current Date is in this Semester
     * @param learningPhase
     */
    public void addLearningPhaseHard(LearningPhase learningPhase){
        for(Week week: weeks){
            int comparisonResult = week.compareTo(learningPhase);
            if(comparisonResult == 0 || comparisonResult == -1){//learning Phase is in that week
                week.addLearningPhase(learningPhase);
            }
        }
    }
}
