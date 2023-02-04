package com.heinsberg.TimeManagementSystem.BackGround.Project.TypeAdapter;

import com.google.gson.GsonBuilder;
import com.heinsberg.TimeManagementSystem.BackGround.Project.Project;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class ProjectTypeAdapterTest {

    @Test
    public void writeTest() {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Project.class, new ProjectTypeAdapter());
        Project projectOnTest = new Project("test");
        projectOnTest.setWeekGoal(60);
        LearningPhase[] learningPhases = new LearningPhase[3];
        Date startDate = new Date(123, 1, 3, 12, 00);
        Date endDate = new Date(123, 1, 3, 12, 30);
        learningPhases[0] = new LearningPhase(startDate.getTime(), endDate.getTime(), projectOnTest);


        startDate = new Date(123, 1, 1, 12, 00);
        endDate = new Date(123, 1, 1, 12, 30);
        learningPhases[1] = new LearningPhase(startDate.getTime(), endDate.getTime(), projectOnTest);

        startDate = new Date(123, 1, 1, 13, 00);
        endDate = new Date(123, 1, 1, 14, 30);
        learningPhases[2] = new LearningPhase(startDate.getTime(), endDate.getTime(), projectOnTest);

        projectOnTest.addLearningPhase(learningPhases[0]);
        projectOnTest.addLearningPhase(learningPhases[1]);
        projectOnTest.addLearningPhase(learningPhases[2]);
        String json = gsonBuilder.create().toJson(projectOnTest);

        System.out.println(json);
        Assertions.assertTrue(json.equals("{\"projectName\":\"test\",\"weekGoal\":60,\"learningPhases\":[{\"startDate\":1675422000000,\"endDate\":1675423800000},{\"startDate\":1675249200000,\"endDate\":1675251000000},{\"startDate\":1675252800000,\"endDate\":1675258200000}]}"));
    }

    @Test
    public void readTest(){

        String json = "{\"projectName\":\"test\",\"weekGoal\":60,\"learningPhases\":[{\"startDate\":1675422000000,\"endDate\":1675423800000},{\"startDate\":1675249200000,\"endDate\":1675251000000},{\"startDate\":1675252800000,\"endDate\":1675258200000}]}";
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Project.class, new ProjectTypeAdapter());

        Project newProject = gsonBuilder.create().fromJson(json, Project.class);

        Assertions.assertTrue(newProject.getName().equals("test"));
        Assertions.assertTrue(newProject.getWeekGoal() == 60);

        LearningPhase[] learningPhases = newProject.getLearningPhases().toArray(new LearningPhase[0]);

        for(LearningPhase learningPhase:learningPhases){

        }

    }

}
