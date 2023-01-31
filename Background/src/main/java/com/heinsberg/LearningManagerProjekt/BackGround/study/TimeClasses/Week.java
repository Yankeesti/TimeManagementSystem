package com.heinsberg.LearningManagerProjekt.BackGround.study.TimeClasses;

import java.util.ArrayList;
import java.util.Date;

import com.heinsberg.LearningManagerProjekt.BackGround.study.subject.Subject;

public class Week extends TimePeriod {
    int numberInSemester;
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
        endDate.setDate(this.getDate() + 7);
        setEndTime(endDate.getTime());

        //setUp
        learningPhases = new ArrayList<LearningPhase>();
        this.numberInSemester = numberInSemester;
    }

    /**
     * Returns the amount of time learned for the given subject during this week.
     *
     * @param subject the subject for which the time learned is calculated
     * @return the time learned in seconds
     */
    public long learnedFor(Subject subject) {
        return 0;
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
        learningPhases.add(learningPhase);
    }

    /**
     * Calculates how much was learned for subject in this Week
     * @param subject
     * @return how much was learned for subject in Minutes
     */
    public int getLearnedFor(Subject subject) {
        long learned = 0;
        for(LearningPhase learningPhase:learningPhases){
            if(learningPhase.getTimeSpentContainer() == subject){
                learned+= learningPhase.getDiffrence()/1000;
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
}
