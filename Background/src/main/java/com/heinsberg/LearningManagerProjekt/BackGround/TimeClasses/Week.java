package com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses;

import java.util.ArrayList;
import java.util.Date;

import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;

public class Week extends TimePeriod {
    int numberInSemester;
    ArrayList<LearningPhase> learningPhases;


    /**
     * Creates a new week
     *
     * @param startDate
     * @param numberInSemester
     */
    public Week(Date startDate, int numberInSemester) {
        super(getMonday(startDate));
        //set end to Sunday
        Date endDate = new Date();
        endDate.setTime(this.getTime());
        endDate.setDate(this.getDate() + 7);
        setEndTime(endDate.getTime());

        this.numberInSemester = numberInSemester;
    }

    /**
     * @param subject - Subject for which is learned
     * @return started LearningPhase
     */
    public LearningPhase startLearningPhase(Subject subject) {
        LearningPhase aktLearningPhase = new LearningPhase(subject);
        learningPhases.add(aktLearningPhase);
        return aktLearningPhase;
    }

    /**
     * returns how much was learned for a Subject in this Week
     * @param subject
     * @return - Time learned in seconds
     */
    public long learnedFor(Subject subject) {
        return 0;
    }

    //Getters and Setter
    public LearningPhase[] getLearningPhases() {
        LearningPhase[] outPut = new LearningPhase[learningPhases.size()];
        for (int i = 0; i < outPut.length; i++) {
            outPut[i] = learningPhases.get(i);
        }
        return outPut;
    }


}
