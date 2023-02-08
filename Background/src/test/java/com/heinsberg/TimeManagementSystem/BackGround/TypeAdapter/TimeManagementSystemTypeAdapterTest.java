package com.heinsberg.TimeManagementSystem.BackGround.TypeAdapter;

import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.TimePeriod;
import com.heinsberg.TimeManagementSystem.BackGround.TimeManagementSystem;
import com.heinsberg.TimeManagementSystem.BackGround.WeekFactory;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TimeManagementSystemTypeAdapterTest {

    @Test
    public void testWrite(){
        TimeManagementSystem timeManagementSystem = new TimeManagementSystem(new WeekFactory());

        Study newStudy = new Study("TEst");

        timeManagementSystem.addStudy(newStudy);

        Date aktDate = TimePeriod.getAktDate();
        aktDate.setDate(aktDate.getDate()+7);
        Semester newSemester = new Semester(3,new Date(123,0,1),aktDate);
        Subject test = new Subject("Test",newSemester,5);


    }
}
