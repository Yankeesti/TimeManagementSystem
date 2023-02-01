package com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses;

import com.heinsberg.LearningManagerProjekt.BackGround.abstractClasses.TimeSpentContainer;

/**
 * Represents a specific period of learning for a subject.
 *
 * @author Tim Heinserg
 */
public class LearningPhase extends TimePeriod {
    private TimeSpentContainer timeSpentContainer;
    private long timeLearned; //Time learned in Seconds

    /**
     * Creates a new LearningPhase object with the current date and time as the start date, and the given subject.
     *
     * @param timeSpentContainer The subject/the Project being studied/worked on during this learning phase.
     */
    public LearningPhase(TimeSpentContainer timeSpentContainer) {
        super(getAktDate());
        this.timeSpentContainer = timeSpentContainer;
    }

    /**
     * Constructor for loading from json File
     * when endDate < 0 no enddate is set (learningPhase didnt endet yet)
     *
     * @param startDate - start date in ms after January 1, 1970, 00:00:00 GMT
     * @param endDate - end date in ms January 1, 1970, 00:00:00 GMT
     */
    public LearningPhase(long startDate,long endDate){
        super(startDate,endDate);
    }

    /**
     * Ends this learning phase and calculates the time spent learning in seconds.
     *
     * @return the time learned in seconds.
     */
    public long endLearningPhase() {
        setEndTime(getAktDate().getTime());
        timeLearned = getDiffrence() / 1000;
        return timeLearned;
    }

    //Getter and Setter

    /**
     * Retrieves the subject studied during this learning phase.
     *
     * @return the subject studied during this learning phase.
     */
    public TimeSpentContainer getTimeSpentContainer() {
        return timeSpentContainer;
    }


    @Override
    public void setEndTime(long time){
        if(time >= 0){
            super.setEndTime(time);
            timeLearned = getDiffrence()/1000;
        }
    }
    public long getTimeLearned(){
        return timeLearned;}

    public void setTimeSpentContainer(TimeSpentContainer timeSpentContainer) {
        this.timeSpentContainer = timeSpentContainer;
    }

}
