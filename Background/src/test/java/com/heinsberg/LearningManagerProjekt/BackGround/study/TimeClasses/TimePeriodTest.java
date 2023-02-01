package com.heinsberg.LearningManagerProjekt.BackGround.study.TimeClasses;

import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.TimePeriod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TimePeriodTest {
    @Test
    public void compareToDateTest() {
        Date begin = new Date(100, 0, 1);
        Date end = new Date(101, 0, 1);
        TimePeriod toTest = new Semester(2, begin, end);

        //Date to test
        Date testDate = new Date(100, 2, 2);

        //Date is in Time Period
        int result = toTest.compareTo(testDate);
        Assertions.assertTrue(result == 0);

        //Date is start of time Period
        testDate = new Date(100, 0, 1);
        result = toTest.compareTo(testDate);
        Assertions.assertTrue(result == 0);
        //Date is end of Time Period
        testDate = new Date(101, 0, 1);
        result = toTest.compareTo(testDate);
        Assertions.assertTrue(result == 0);

        //Date is before Time Period
        testDate = new Date(99, 0, 1);
        result = toTest.compareTo(testDate);
        Assertions.assertTrue(result > 0);

        //Date is after Time Period
        testDate = new Date(103, 0, 1);
        result = toTest.compareTo(testDate);
        Assertions.assertTrue(result < 0);
    }

    @Test
    public void compareToTimePeriodTest() {
        Date begin1 = new Date(100, 0, 1);
        Date end1 = new Date(101, 0, 1);
        TimePeriod toTest1 = new Semester(2, begin1, end1);

        //Argument Time Period is in Time Period
        Date begin2 = new Date(100, 0, 1);
        Date end2 = new Date(101, 0, 1);
        TimePeriod toTest2 = new Semester(2, begin2, end2);
        Assertions.assertTrue(toTest1.compareTo(toTest2) == 0);

        begin2 = new Date(100, 1, 1);
        end2 = new Date(100, 5, 1);
        toTest2 = new Semester(2, begin2, end2);
        Assertions.assertTrue(toTest1.compareTo(toTest2) == 0);

        //Argument Time Period encompasses thisTimePeriod
        begin2 = new Date(99, 1, 1);
        end2 = new Date(105, 5, 1);
        toTest2 = new Semester(2, begin2, end2);
        System.out.println(toTest1.compareTo(toTest2));
        Assertions.assertTrue(toTest1.compareTo(toTest2) == Integer.MAX_VALUE);

        //Argument Time Period is completly before this Time Period
        begin2 = new Date(50, 1, 1);
        end2 = new Date(80, 5, 1);
        toTest2 = new Semester(2, begin2, end2);
        Assertions.assertTrue(toTest1.compareTo(toTest2) == 2);

        //Argument Time Period is completly after this Time Period
        begin2 = new Date(103, 1, 1);
        end2 = new Date(140, 5, 1);
        toTest2 = new Semester(2, begin2, end2);
        Assertions.assertTrue(toTest1.compareTo(toTest2) == -2);

        //Argument Time Period starts in this time period but end in another
        begin2 = new Date(100, 3, 1);
        end2 = new Date(110, 5, 1);
        toTest2 = new Semester(2, begin2, end2);
        Assertions.assertTrue(toTest1.compareTo(toTest2) == -1);

        //Argument Time Period end in this timePeriod but begins before
        begin2 = new Date(99, 1, 1);
        end2 = new Date(100, 5, 1);
        toTest2 = new Semester(2, begin2, end2);
        Assertions.assertTrue(toTest1.compareTo(toTest2) == 1);

    }
}
