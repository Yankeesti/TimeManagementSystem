package com.heinsberg.TimeManagementSystem.BackGround;

import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.Week;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * The WeekFactory class is responsible for providing all other objects in the Project with their corresponding weeks,
 * ensuring that a week only occurs once.
 */
public class WeekFactory {
    private ArrayList<Week> weeks;

    private Week currentWeek;
    public WeekFactory() {
        weeks = new ArrayList<Week>();
    }


    /**
     * Returns the Week that contains the Current date ,
     * when there is no week that Includes the Current date a new gets created
     *
     * @return Week that contains the current Date
     */
    public Week getCurrentWeek() {
        Date aktDate = getAktDate();
        if(currentWeek != null && currentWeek.compareTo(aktDate) == 0){
            return currentWeek;
        }else{
            return currentWeek = getWeek(aktDate);
        }
    }

    /**
     * Returns the week to which date belongs
     *
     * @param date - date that is in Week
     * @return Week in which date is in
     */
    public Week getWeek(Date date) {
        if (weeks.isEmpty()) {// when there is no Week yet
            return addNewWeek(date);
        }
        if (weeks.size() == 1) {//when there is only one Week check weather this Week contains date
            if (weeks.get(0).compareTo(date) != 0) {//Week does not conatain date --> create new Week
                return addNewWeek(date);
            } else {
                return weeks.get(0);//return the given Week
            }
        } else { //there are more than one Week
            if (!weeksContain(date)) {//when date is not included add a new Week
                return addNewWeek(date);
            } else {//check weather the week that contains date is already in weeks
                for (Week week : weeks) {
                    if (week.compareTo(date) == 0)
                        return week; //when week contains Date return it
                }
                //when no week contains date add a new one
                return addNewWeek(date);
            }
        }
    }

    public ArrayList<Week> getWeeks() {
        return weeks;
    }

    /**
     * Checks weather date is contained in the Time Span from oldest to newest Week
     *
     * @param date - Date to be checked
     * @return true when date is included in the time span and false when not
     */
    private boolean weeksContain(Date date) {
        if (((Date) (weeks.get(0))).compareTo(date) <= 0 && //start of weeks is before the date argument
                ((Date) (weeks.get(weeks.size() - 1))).compareTo(date) >= 0) //end of weeks is after the date Argument
            return true;
        return false;
    }

    /**
     * creates a new Week , adds it to weeks sorts the list and returns the new Week
     *
     * @param date - date which Week should contain
     * @return - the new Week
     */
    private Week addNewWeek(Date date) {
        Week newWeek = new Week(date);
        weeks.add(newWeek);
        sortWeeks();
        return newWeek;
    }

    /**
     * Sorts the Weeks from oldest to newest Week
     */
    private void sortWeeks() {
        weeks.sort((Week o1, Week o2) -> {
            return (((Date) o1).compareTo((Date) o2)); //sorts the Weeks
        });
    }

    /**
     * Returns the current date.
     *
     * @return The current date.
     */
    private Date getAktDate() {
        Date outPut = new Date();
        LocalDateTime now = LocalDateTime.now();

        outPut.setYear(now.getYear() - 1900);
        outPut.setMonth(now.getMonthValue() - 1);
        outPut.setHours(now.getHour());
        outPut.setMinutes(now.getMinute());
        outPut.setSeconds(now.getSecond());

        return outPut;
    }

    public ObservableList<LearningPhase> getLearningPhases(Semester semester) {
        ObservableList<LearningPhase> outPut = FXCollections.observableArrayList();
        for(Week week: weeks){
            outPut.addAll(week.getLearningPhases(semester));
        }
        outPut.sort((Date l1, Date l2) ->{
            return l1.compareTo(l2);
        });
        return outPut;
    }
}
