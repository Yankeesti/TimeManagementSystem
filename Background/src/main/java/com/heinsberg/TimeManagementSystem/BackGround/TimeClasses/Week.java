package com.heinsberg.TimeManagementSystem.BackGround.TimeClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;

public class Week extends TimePeriod {
    int numberInSemester;
    int weekNumber;//Calendar Week number
    ArrayList<LearningPhase> learningPhases;


    /**
     * Creates a new week with the given start date and number in the semester.
     * The startDate is automatically set to the Monday of the Week and
     * The end date is automatically set to the following Sunday.
     *
     * @param startDate        the start date of the week
     * @param numberInSemester the number of this week in the semester
     */

    public Week(Date startDate, int numberInSemester) {
        super(getMonday(startDate));
        //set end to Sunday
        Date endDate = new Date();
        endDate.setTime(this.getTime());
        endDate.setDate(this.getDate() + 67);
        setEndTime(endDate.getTime());
        learningPhases = new ArrayList<LearningPhase>();
        this.numberInSemester = numberInSemester;
        calculateWeekNumber();
    }

    public Week(Date startDate){
        super(getMonday(startDate));
        //set end to Sunday
        Date endDate = new Date();
        endDate.setTime(this.getTime());
        endDate.setDate(this.getDate() + 7);
        endDate.setTime(endDate.getTime()-1);
        setEndTime(endDate.getTime());
        learningPhases = new ArrayList<LearningPhase>();
        calculateWeekNumber();
    }



    private void calculateWeekNumber() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this);
        weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
    }

    //Getters and Setter

    /**
     Returns an array of all the learning phases that took place during this week.
     @return an array of learning phases
     */
    public LearningPhase[] getLearningPhases() {
        LearningPhase[] outPut = new LearningPhase[learningPhases.size()];
        for (int i = 0; i < outPut.length; i++) {
            outPut[i] = learningPhases.get(i);
        }
        return outPut;
    }


    public void addLearningPhase(LearningPhase learningPhase) {
        if(!learningPhases.contains(learningPhase))
        learningPhases.add(learningPhase);
    }

    /**
     * Calculates how much was learned for subject in this Week
     * @param timeSpentContainer
     * @return how much was learned for subject in Minutes
     */
    public int getLearnedFor(TimeSpentContainer timeSpentContainer) {
        long learned = 0;
        for(LearningPhase learningPhase:learningPhases){
            if(learningPhase.getTimeSpentContainer() == timeSpentContainer){
                learned+= learningPhase.getDifference()/1000;
            }
        }
        return (int) (learned/60);
    }


    /**
     * deletes The given Learning Phase
     * @param learningPhase
     */
    public void deleteLearningPhase(LearningPhase learningPhase) {
        learningPhases.remove(learningPhase);
    }

    public void removeLearningPhase(LearningPhase learningPhase) {
        learningPhases.remove(learningPhase);
    }

    /**
     * Returns all LearningPhases that belong to Subjects in Semester
     * @param semester
     * @return
     */
    public ArrayList<LearningPhase> getLearningPhases(Semester semester) {
        ArrayList<LearningPhase> outPut = new ArrayList<LearningPhase>();
        for(LearningPhase learningPhase:learningPhases){
            if(learningPhase.getTimeSpentContainer() instanceof Subject && ((Subject)learningPhase.getTimeSpentContainer()).getSemester() == semester){
                outPut.add(learningPhase);
            }
        }
        outPut.sort((Date l1, Date l2) ->{
            return l1.compareTo(l2);
        });
        return outPut;
    }
    @Override
    public String toString(){
        return weekNumber+"";
    }

    public int getWeekNumber() {
        return weekNumber;
    }
}
