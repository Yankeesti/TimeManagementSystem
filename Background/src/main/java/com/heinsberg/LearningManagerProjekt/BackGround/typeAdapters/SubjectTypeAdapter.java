package com.heinsberg.LearningManagerProjekt.BackGround.typeAdapters;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SubjectTypeAdapter extends TypeAdapter<Subject> {
    @Override
    public void write(JsonWriter jsonWriter, Subject subject) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(LearningPhase.class, new LearningPhaseAdapter());
        LearningPhase[] learningPhases = subject.getLearningPhases().toArray(new LearningPhase[0]);
        jsonWriter.beginObject();
        jsonWriter.name("finalGrade").value(subject.getFinalGrade());
        jsonWriter.name("ectsPoints").value(subject.getEctsPoints());
        jsonWriter.name("weekGoal").value(subject.getWeekGoal());
        jsonWriter.name("subjectName").value(subject.getSubjectName());
        jsonWriter.name("learningPhases");
        jsonWriter.beginArray();
        for (LearningPhase learningPhase : learningPhases) {
            gsonBuilder.create().toJson(learningPhase, LearningPhase.class, jsonWriter); //writes LearningPhase
        }
        jsonWriter.endArray();
        jsonWriter.endObject();
    }

    @Override
    public Subject read(JsonReader jsonReader) throws IOException {
        double finalGrade = 0;
        String subjectName = null;
        int ectsPoint = 0;
        int weekGoal = 0;
        jsonReader.beginObject();
        ArrayList<LearningPhase> learningPhases = new ArrayList<LearningPhase>();
        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "finalGrade":
                    finalGrade = jsonReader.nextDouble();
                    break;
                case "ectsPoints":
                    ectsPoint = jsonReader.nextInt();
                    break;
                case "weekGoal":
                    weekGoal = jsonReader.nextInt();
                    break;
                case "subjectName":
                    subjectName = jsonReader.nextString();
                    break;
                case "learningPhases":
                    learningPhases = readLearningPhases(jsonReader);
            }
        }
        jsonReader.endObject();
        Subject outPut = new Subject(subjectName, null, ectsPoint);
        outPut.setWeekGoal(weekGoal);
        outPut.setFinalGrade(finalGrade);
        for (LearningPhase learningPhase : learningPhases){
            outPut.addLearningPhase(learningPhase);
            learningPhase.setSubject(outPut);
        }
        return outPut;
    }

    private ArrayList<LearningPhase> readLearningPhases(JsonReader jsonReader) {
        ArrayList<LearningPhase> outPut = new ArrayList<LearningPhase>();
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(LearningPhase.class, new LearningPhaseAdapter());
        Type listType = new TypeToken<ArrayList<LearningPhase>>() {
        }.getType();
        outPut = gsonBuilder.create().fromJson(jsonReader, listType);
        return outPut;
    }
}
