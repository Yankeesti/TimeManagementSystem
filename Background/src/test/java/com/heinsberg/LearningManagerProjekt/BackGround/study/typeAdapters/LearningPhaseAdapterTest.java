package com.heinsberg.LearningManagerProjekt.BackGround.study.typeAdapters;

public class LearningPhaseAdapterTest {

//    @Test
//    public void testWriting(){
//        LearningPhase testObject = new LearningPhase(null);
//        testObject.setTime(300,500);
//        GsonBuilder gson = new GsonBuilder().registerTypeAdapter(LearningPhase.class,new StudyTypeAdapter.LearningPhaseAdapter());
//        String json = gson.create().toJson(testObject);
//        Assertions.assertTrue(json.equals("{\"startDate\":300,\"endDate\":500}"));
//
//        testObject = new LearningPhase(null);
//        testObject.setTime(300);
//        json = gson.create().toJson(testObject);
//        Assertions.assertTrue(json.equals("{\"startDate\":300,\"endDate\":-1}"));
//    }
//
//    @Test
//    public void testReading(){
//        GsonBuilder gson = new GsonBuilder().registerTypeAdapter(LearningPhase.class,new StudyTypeAdapter.LearningPhaseAdapter());
//        String json = "{\"startDate\":300,\"endDate\":-1}";
//        LearningPhase test = gson.create().fromJson(json, LearningPhase.class);
//        Assertions.assertTrue(test.getTime() == 300);
//        Assertions.assertTrue(test.getEndDate() == null);
//
//        json = "{\"startDate\":300,\"endDate\":1300}";
//        test = gson.create().fromJson(json, LearningPhase.class);
//        Assertions.assertTrue(test.getTime() == 300);
//        Assertions.assertTrue(test.getEndDate().getTime() == 1300);
//        Assertions.assertTrue(test.getTimeLearned() == 1);
//    }
}
