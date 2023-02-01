package com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses;

import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.TimePeriod;
import com.heinsberg.TimeManagementSystem.BackGround.study.AddSemesterResult;
import com.heinsberg.TimeManagementSystem.BackGround.study.AddSubjectResult;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class StudyTest {

    Study underTest;


    @Test
   public void testStartLearningPhase() {


    }

    @Test
    public void testFinishLearningPhase() {
        //given
        underTest = new Study("Test Study");
        //when
        long outcome = underTest.finishLearningPhase();
        //then
        Assertions.assertTrue(outcome == -1);

        //when
        Date aktDate = TimePeriod.getAktDate();
        aktDate.setDate(aktDate.getDate()+7);
        Semester newSemester = new Semester(3,new Date(123,0,1),aktDate);
        Subject test = new Subject("Test",newSemester,5);

        underTest.addSemester(newSemester);
        underTest.addSubject(test);
        underTest.startLearningPhase(test);
        outcome = underTest.finishLearningPhase();
        //then
        Assertions.assertTrue(outcome>=0);



    }

    /**
     * Tests the addSemester MEthod
     */
    @Test
    public void testAddSemesterTest() {
        //given
        underTest = new Study("Test Study");
        //when
        //first Semester added
        Semester newSemester = new Semester(3, new Date(122, 9, 19), new Date(123, 2, 3));
        AddSemesterResult outcome = underTest.addSemester(newSemester);
        //then
        Assertions.assertTrue(outcome == AddSemesterResult.SUCCESS);

        //when
        //second Semester added
        Semester newSemester2 = new Semester(4, new Date(123, 3, 20), new Date(123, 7, 14));
        outcome = underTest.addSemester(newSemester2);
        //then
        Assertions.assertTrue(outcome == AddSemesterResult.SUCCESS);

        //when
        //third Semester a Semester added with same SemesterNumber as new Semester
        Semester newSemester3 = new Semester(3, new Date(200, 3, 20), new Date(200, 7, 14));
        outcome = underTest.addSemester(newSemester3);
        //then
        Assertions.assertFalse(outcome == AddSemesterResult.SUCCESS);

        //when
        //third Semester added that is in Semester 4
        Semester newSemester4 = new Semester(3, new Date(123, 7, 10), new Date(125, 7, 14));
        outcome = underTest.addSemester(newSemester4);
        //then
        Assertions.assertFalse(outcome == AddSemesterResult.SUCCESS);
    }

    /**
     * Tests the addSubject Method
     */
    @Test
    public void testAddSubjectTest() {
        //given
        underTest = new Study("Test Study");
        underTest.addSemester(new Semester(3, new Date(122, 9, 19), new Date(123, 2, 3)));
        underTest.addSemester(new Semester(1, new Date(121, 9, 19), new Date(122, 2, 3)));

        //when Adding Subject to semester 3
        AddSubjectResult outcome = underTest.addSubject(new Subject("Technische Informatik",underTest.getSemester(3),5));
        //then
        Assertions.assertTrue(outcome == AddSubjectResult.SUCCESS);

        //when Subject is Added and Semester is not in study
        outcome = underTest.addSubject(new Subject("Test",underTest.getSemester(5),5));
        //then
        Assertions.assertFalse(outcome == AddSubjectResult.SUCCESS);

        //when Subject is Added that has the same name as already added Subject and Semester
        outcome = underTest.addSubject(new Subject("Technische Informatik",underTest.getSemester(3),59));
        //then
        Assertions.assertFalse(outcome == AddSubjectResult.SUCCESS);

        //when Subject is Added with same Name but in other Semester
        outcome = underTest.addSubject(new Subject("Technische Informatik",underTest.getSemester(1),5));
        //then
        Assertions.assertTrue(outcome == AddSubjectResult.SUCCESS);


    }

    @Test
    public void testGetSemesters(){
        //given
        underTest = new Study("Test Study");
        //Added unsorted
        underTest.addSemester(new Semester(4, new Date(123, 3, 20), new Date(123, 7, 14)));
        underTest.addSemester(new Semester(5, new Date(123, 9, 25), new Date(124, 2, 9)));
        underTest.addSemester(new Semester(3, new Date(122, 9, 19), new Date(123, 2, 3)));
        //when
        ObservableList<Semester> outcome = underTest.getSemesters();
        //then
        Assertions.assertTrue(outcome.get(0).getSemester() == 3);
        Assertions.assertTrue(outcome.get(1).getSemester() == 4);
        Assertions.assertTrue(outcome.get(2).getSemester() == 5);
    }

    //Life cycle
}