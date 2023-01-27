package com.heinsberg.LearningManagerProjekt.BackGround.typeAdapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class StudyAdapterTest {

    @Test
    public void writeSudyTest(){
        GsonBuilder gson = new GsonBuilder().registerTypeAdapter(Study.class,new StudyTypeAdapter());
        Study study = new Study("Technische Informatik");
        Semester[] semesters = new Semester[3];
        semesters[0] = new Semester(3, new Date(122,8,1),new Date(123,1,28));
        semesters[1] = new Semester(4, new Date(123,2,1),new Date(123,7,31));
        semesters[2] = new Semester(5, new Date(123,8,1),new Date(124,1,29));
        study.addSemester(semesters[0]);
        study.addSemester(semesters[1]);
        study.addSemester(semesters[2]);

        //Adding Subjects
        Subject[] subjects = new Subject[3];

        subjects[0] = new Subject("EG",semesters[0],5); // in Semester 3
        subjects[1] = new Subject("Ad",semesters[1],5); // in Semester 4
        subjects[2] = new Subject("TEstSubject",semesters[1],5); // in Semester 4
        study.addSubject(subjects[0]);
        study.addSubject(subjects[1]);
        study.addSubject(subjects[2]);

        LearningPhase[] test = new LearningPhase[4];
        test[0] = new LearningPhase(subjects[0]);
        test[0].setTime(semesters[0].getTime()+500,semesters[0].getEndDate().getTime() -3000);

        test[1] = new LearningPhase(subjects[1]);
        test[1].setTime(semesters[1].getTime()+500,semesters[1].getEndDate().getTime() -3000);

        test[3] = new LearningPhase(subjects[0]);
        test[3].setTime(semesters[0].getTime()+1500,semesters[0].getEndDate().getTime() -3000);

        test[2] = new LearningPhase(subjects[2]);
        test[2].setTime(semesters[1].getTime()+500,semesters[1].getEndDate().getTime() -3000);

        study.addLearningPhase(test[0]);
        study.addLearningPhase(test[1]);
        study.addLearningPhase(test[2]);
        study.addLearningPhase(test[3]);

        String json = gson.create().toJson(study);
        Assertions.assertTrue(json.equals("{\"study Name\":\"Technische Informatik\",\"semesters\":[{\"semester\":3,\"start\":1661983200000,\"end\":1677538800000,\"subjects\":[{\"finalGrade\":0.0,\"ectsPoints\":5,\"weekGoal\":0,\"subjectName\":\"EG\",\"learningPhases\":[{\"startDate\":1661983200500,\"endDate\":1677538797000},{\"startDate\":1661983201500,\"endDate\":1677538797000}]}]},{\"semester\":4,\"start\":1677625200000,\"end\":1693432800000,\"subjects\":[{\"finalGrade\":0.0,\"ectsPoints\":5,\"weekGoal\":0,\"subjectName\":\"Ad\",\"learningPhases\":[{\"startDate\":1677625200500,\"endDate\":1693432797000}]},{\"finalGrade\":0.0,\"ectsPoints\":5,\"weekGoal\":0,\"subjectName\":\"TEstSubject\",\"learningPhases\":[{\"startDate\":1677625200500,\"endDate\":1693432797000}]}]},{\"semester\":5,\"start\":1693519200000,\"end\":1709161200000,\"subjects\":[]}]}"));
    }

    @Test
    public void readStudyTest(){
        String json = "{\"study Name\":\"Technische Informatik\",\"semesters\":[{\"semester\":3,\"start\":1661983200000,\"end\":1677538800000,\"subjects\":[{\"finalGrade\":0.0,\"ectsPoints\":5,\"weekGoal\":0,\"subjectName\":\"EG\",\"learningPhases\":[{\"startDate\":1661983200500,\"endDate\":1677538797000},{\"startDate\":1661983201500,\"endDate\":1677538797000}]}]},{\"semester\":4,\"start\":1677625200000,\"end\":1693432800000,\"subjects\":[{\"finalGrade\":0.0,\"ectsPoints\":5,\"weekGoal\":0,\"subjectName\":\"Ad\",\"learningPhases\":[{\"startDate\":1677625200500,\"endDate\":1693432797000}]},{\"finalGrade\":0.0,\"ectsPoints\":5,\"weekGoal\":0,\"subjectName\":\"TEstSubject\",\"learningPhases\":[{\"startDate\":1677625200500,\"endDate\":1693432797000}]}]},{\"semester\":5,\"start\":1693519200000,\"end\":1709161200000,\"subjects\":[]}]}";
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Study.class, new StudyTypeAdapter());
        Study outPut = gsonBuilder.create().fromJson(json,Study.class);
        String jsonOutPut = gsonBuilder.create().toJson(outPut);
        Assertions.assertTrue(json.equals(jsonOutPut));

    }
}
