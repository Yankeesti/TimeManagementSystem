package com.heinsberg.TimeManagementSystem.BackGround;

import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.Week;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

public class WeekFactoryTest {

    @Test
    public void getWeekTest(){
        WeekFactory underTest = new WeekFactory();
        ArrayList<Week> weeks = underTest.getWeeks();

        Assertions.assertTrue(weeks.size() == 0);

        //Add new Week to Weeks
        Date date = new Date(122,0,1);
        Week week = underTest.getWeek(date);
        Assertions.assertTrue(weeks.size() == 1); // one week is added
        Assertions.assertTrue(week.compareTo(date) == 0);//week contains date

        //Add a other week to weeks
        date = new Date(123,0,1);
        week = underTest.getWeek(date);
        Assertions.assertTrue(weeks.size() == 2); // one week is added
        Assertions.assertTrue(week.compareTo(date) == 0);//week contains date

        //Add a Week that is inside of the range of weeks
        date = new Date(122,3,1);
        week = underTest.getWeek(date);
        Assertions.assertTrue(weeks.size() == 3); // one week is added
        Assertions.assertTrue(week.compareTo(date) == 0);//week contains date

        //Add a Week that is outside of the range of Weeks
        date = new Date(124,0,1);
        week = underTest.getWeek(date);
        Assertions.assertTrue(weeks.size() == 4); // one week is added
        Assertions.assertTrue(week.compareTo(date) == 0);//week contains date

        //get a Week from a date that is already included
        date = new Date(122,2,30);
        week = underTest.getWeek(date);
        Assertions.assertTrue(weeks.size() == 4); //no Week is added
        Assertions.assertTrue(week.compareTo(date) == 0);//week contains date
    }
}
