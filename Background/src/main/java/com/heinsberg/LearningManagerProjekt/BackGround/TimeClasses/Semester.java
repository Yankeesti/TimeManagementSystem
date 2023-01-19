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
    public Semester(int semester, Date semesterStart, Date semesterEnd){
        super(semesterStart,semesterEnd);
        this.semester = semester;
        Date startMonday = getMonday(semesterStart);
        weeks = new Week[calculateWeekAmount()];
        for(int i = 0; i<weeks.length; i++) {
            weeks[i] = new Week(startMonday,i);
            startMonday.setDate(startMonday.getDate()+7);
        }
        currentWeekIndex = 0;
        subjects = new ArrayList<Subject>();
    }

    //Control Methods
    /**
     * starts a Learning Phase
     * Study need to control that this Semester is the current Semester
     * @param subject - Subject for which is learned
     * @return started LearningPhase
     */
    public  LearningPhase startLearningPhase(Subject subject){
        upDateWeek();
        return weeks[currentWeekIndex].startLearningPhase(subject);
    }

    //Getter and Setter
     /**
     * adds p to subjects
     * @param subject - Subject to add
     */
    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public int getSemester(){
        return semester;
    }


    //Private Methods
    /**
     * @return number of Weeks in this Semester
     */
    private int calculateWeekAmount() {
        Date startMonday = getMonday(this);
        Date endMonday = getMonday(endDate);
        int anzahl = 0;

        while(startMonday.compareTo(endMonday)<= 0) {
            startMonday.setDate(startMonday.getDate()+7);
            anzahl ++;
        }
        return anzahl;
    }

    private void upDateWeek(){
        Date aktDate = getAktDate();
        if(compareTo(aktDate) != 0)//Check if current Date is in this Semester
            currentWeekIndex = -1;
        for(int i = currentWeekIndex;i<weeks.length;i++){
            if(weeks[i].compareTo(aktDate) == 0){
                currentWeekIndex = i;
                break;
            }
        }
     }
}
