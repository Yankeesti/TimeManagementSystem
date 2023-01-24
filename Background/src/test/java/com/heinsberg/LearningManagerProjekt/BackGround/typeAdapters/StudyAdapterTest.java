package com.heinsberg.LearningManagerProjekt.BackGround.typeAdapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class StudyAdapterTest {

    @Test
    public void writeSudyTest(){
        GsonBuilder gson = new GsonBuilder().registerTypeAdapter(Study.class,new StudyTypeAdapter()).setPrettyPrinting();
        Study study = new Study("Technische Informatik");
        Semester[] semesters = new Semester[3];
        semesters[0] = new Semester(3, new Date(122,8,1),new Date(123,1,28));
        semesters[1] = new Semester(4, new Date(123,2,1),new Date(123,7,31));
        semesters[2] = new Semester(5, new Date(123,8,1),new Date(124,1,29));
        study.addSemester(semesters[0]);
        study.addSemester(semesters[1]);
        study.addSemester(semesters[2]);

        //Adding Subjects
        Subject[] subjects = new Subject[2];

        subjects[0] = new Subject("EG",3,5);
        subjects[1] = new Subject("Ad",4,5);
        study.addSubject(subjects[0]);
        study.addSubject(subjects[1]);

        LearningPhase[] test = new LearningPhase[3];
        test[0] = new LearningPhase(subjects[0]);
        test[0].setTime(semesters[0].getTime()+500,semesters[0].getEndDate().getTime() -3000);

        test[1] = new LearningPhase(subjects[1]);
        test[1].setTime(semesters[1].getTime()+500,semesters[1].getEndDate().getTime() -3000);

        study.addLearningPhase(test[0]);
        study.addLearningPhase(test[1]);

        String json = gson.create().toJson(study);
        System.out.println(json);

    }
}
