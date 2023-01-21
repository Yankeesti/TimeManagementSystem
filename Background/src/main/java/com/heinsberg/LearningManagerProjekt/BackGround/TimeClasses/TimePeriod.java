package com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Represents a time period that extends the functionality of the {@link Date} class.
 * Provides methods for comparing time periods and setting start and end times.
 */
public abstract class TimePeriod extends Date {
    protected Date endDate;

    /**
     * Creates a Time Period with a start and end date
     *
     * @param startDate the start date of the time period
     * @param endDate   the end date of the time period
     */
    public TimePeriod(Date startDate, Date endDate) {
        setTime(startDate.getTime());
        this.endDate = endDate;
    }

    /**
     * Creates a Time Period with an open-ended start date.
     *
     * @param startDate the start date of the time period
     */
    public TimePeriod(Date startDate) {
        setTime(startDate.getTime());
    }

    /**
     * Compares a TimePeriod and a Date for ordering.
     *
     * @param anotherDate the date to compare
     * @return the value 0 if the argument Date is in the Time Period; a value less than 0 if this Time Period is before the Date argument; and a value greater than 0 if this Time Period is after the Date argument.
     */
    public int compareTo(Date anotherDate) {

        if ((endDate != null && super.compareTo(anotherDate) <= 0 && endDate.compareTo(anotherDate) >= 0) || super.compareTo(anotherDate) == 0 || (endDate == null && super.compareTo(anotherDate) <= 0))
            return 0;

        if (super.compareTo(anotherDate) < 0)
            return -1;

        return 1;
    }

    /**
     * Compares two TimePeriods for ordering
     *
     * @param anotherTimePeriod - TimePeriod to compare
     * @return the value 0 if the argument TimePeriod is in the Time Period; -1 if the Time PeriodArgument start is in this time Period but the ending is after this Time Period; -2 if the Time Period Argument is after this Time Period; 1 if the Time Period ends is in this time Period but the start is before this Time Period; 2 if the Time Period Argument is before this Time Period
     */
    public int compareTo(TimePeriod anotherTimePeriod) {
        if (super.compareTo(anotherTimePeriod) <= 0 && endDate.compareTo(anotherTimePeriod.getEndDate()) >= 0)
            return 0;

        if (super.compareTo(anotherTimePeriod) <= 0 && endDate.compareTo(anotherTimePeriod) >= 0)
            return -1;

        if (endDate.compareTo(anotherTimePeriod) < 0)
            return -2;

        if (super.compareTo(anotherTimePeriod.getEndDate()) > 0)
            return 2;

        return 1;
    }

    /**
     * Sets the start and end time of the TimePeriod object.
     *
     * @param startTime the number of milliseconds for the start Point
     * @param endTime   the number of milliseconds for the end Point
     */
    public void setTime(long startTime, long endTime) {
        setTime(startTime);
        if (endDate != null)
            endDate.setTime(endTime);
        else {
            endDate = new Date();
            endDate.setTime(endTime);
        }

    }

    /**
     * Sets the End Time
     *
     * @param endTime - end Time of Period in ms after January 1, 1970 00:00:00 GMT.
     */
    public void setEndTime(long endTime) {
        if (endDate != null)
            endDate.setTime(endTime);
        else {
            endDate = new Date();
            endDate.setTime(endTime);
        }
    }


    //Getter and Setter
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Returns the current date.
     *
     * @return The current date.
     */
    public static Date getAktDate() {
        Date outPut = new Date();
        LocalDateTime now = LocalDateTime.now();

        outPut.setYear(now.getYear() - 1900);
        outPut.setMonth(now.getMonthValue() - 1);
        outPut.setHours(now.getHour());
        outPut.setMinutes(now.getMinute());
        outPut.setSeconds(now.getSecond());

        return outPut;
    }

    /**
     * Calculates the difference in milliseconds between the start date and end date of this time period.
     *
     * @return the difference in milliseconds between the start date and end date of this time period.
     */
    public long getDiffrence() {
        return endDate.getTime() - getTime();
    }

    /**
     * Retrieves the Monday of the week for the specified day.
     *
     * @param pDay - The day for which to retrieve the corresponding Monday
     * @return A Date object representing the Monday of the week for the specified day
     */
    protected static Date getMonday(Date pDay) {
        //Montag finden
        Date Monday;
        int day = pDay.getDay();

        //Da in der Date Klasse Sonntag an stelle 0 ist rücken wir hiermit den Montag auf stelle null
        if (day == 0)// wenn sonntag = 6
            day = 6;
        else //initial für tag um eins nach hinten schieben um montag an 0 zu haben
            day -= 1;

        Monday = new Date(pDay.getYear(), pDay.getMonth(), pDay.getDate() - day);
        return Monday;
    }
}
