package com.heinsberg.LearningManagerProjekt.BackGround;

import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    Study underTest;

    @Test
    void startLearningPhase() {
        //given
        //when
        int outcome = underTest.startLearningPhase(new Subject("Test Subject", 2, 5));
        //then

    }

    @Test
    void finishLearningPhase() {
    }

    /**
     * Tests the addSemester MEthod
     */
    @Test
    void addSemesterTest() {
        //given
        //when
        //first Semester added
        Semester newSemester = new Semester(3, new Date(122, 9, 19), new Date(123, 2, 3));
        boolean outcome = underTest.addSemester(newSemester);
        //then
        Assertions.assertTrue(outcome);

        //when
        //second Semester added
        Semester newSemester2 = new Semester(4, new Date(123, 3, 20), new Date(123, 7, 14));
        outcome = underTest.addSemester(newSemester2);
        //then
        Assertions.assertTrue(outcome);

        //when
        //third Semester a Semester added with same SemesterNumber as new Semester
        Semester newSemester3 = new Semester(3, new Date(200, 3, 20), new Date(200, 7, 14));
        outcome = underTest.addSemester(newSemester3);
        //then
        Assertions.assertFalse(outcome);

        //when
        //third Semester added that is in Semester 4
        Semester newSemester4 = new Semester(3, new Date(123, 7, 10), new Date(125, 7, 14));
        outcome = underTest.addSemester(newSemester4);
        //then
        Assertions.assertFalse(outcome);
    }

    /**
     * Tests the addSubject Method
     */
    @Test
    void addSubjectTest() {
        //given
        underTest.addSemester(new Semester(3, new Date(122, 9, 19), new Date(123, 2, 3)));
        underTest.addSemester(new Semester(1, new Date(121, 9, 19), new Date(122, 2, 3)));

        //when Adding Subject to semester 3
        boolean outcome = underTest.addSubject(new Subject("Technische Informatik",3,5));
        //then
        Assertions.assertTrue(outcome);

        //when Subject is Added and Semester is not in study
        outcome = underTest.addSubject(new Subject("Test",5,5));
        //then
        Assertions.assertFalse(outcome);

        //when Subject is Added that has the same name as already added Subject and Semester
        outcome = underTest.addSubject(new Subject("Technische Informatik",3,59));
        //then
        Assertions.assertFalse(outcome);

        //when Subject is Added with same Name but in other Semester
        outcome = underTest.addSubject(new Subject("Technische Informatik",1,5));
        //then
        Assertions.assertTrue(outcome);


    }

    //Life cycle
    @BeforeEach
    public void setup() {
        underTest = new Study("Test Study");
    }
}