package com.heinsberg.TimeManagementSystem.BackGround.study;

import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.Week;
import com.heinsberg.TimeManagementSystem.BackGround.WeekFactory;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class StudyTest {
    @Test
    public void testSurroundingSemester(){
        WeekFactory weekFactory = new WeekFactory();
        Study testObject = new Study("test",weekFactory);

        Semester[] surroundingSemester = testObject.getSurroundingSemesters(3);
        Assertions.assertTrue(surroundingSemester[0] == null);
        Assertions.assertTrue(surroundingSemester[1] == null);

        Date startDate = new Date(122,8,1);
        Date endDate = new Date(123,1,28);
        Semester third = new Semester(3,startDate,endDate,weekFactory);
        testObject.addSemester(third);

        surroundingSemester = testObject.getSurroundingSemesters(1);
        Assertions.assertTrue(surroundingSemester[0] == null);
        Assertions.assertTrue(surroundingSemester[1] == third);

        surroundingSemester = testObject.getSurroundingSemesters(4);
        Assertions.assertTrue(surroundingSemester[0] == third);
        Assertions.assertTrue(surroundingSemester[1] == null);

        //Add a other Semester
        Date startDate2 = new Date(123,8,1);
        Date endDate2 = new Date(124,1,29);
        Semester fifth = new Semester(5,startDate2,endDate2,weekFactory);
        testObject.addSemester(fifth);

        surroundingSemester = testObject.getSurroundingSemesters(1);
        Assertions.assertTrue(surroundingSemester[0] == null);
        Assertions.assertTrue(surroundingSemester[1] == third);

        surroundingSemester = testObject.getSurroundingSemesters(4);
        Assertions.assertTrue(surroundingSemester[0] == third);
        Assertions.assertTrue(surroundingSemester[1] == fifth);

        surroundingSemester = testObject.getSurroundingSemesters(6);
        Assertions.assertTrue(surroundingSemester[0] == fifth);
        Assertions.assertTrue(surroundingSemester[1] == null);


    }
}
