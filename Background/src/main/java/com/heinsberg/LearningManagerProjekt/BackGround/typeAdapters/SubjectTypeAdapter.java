package com.heinsberg.LearningManagerProjekt.BackGround.typeAdapters;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SubjectTypeAdapter extends TypeAdapter<Subject> {
    @Override
    public void write(JsonWriter jsonWriter, Subject subject) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(LearningPhase.class,new LearningPhaseAdapter());
        LearningPhase[] learningPhases = subject.getLearningPhases().toArray(new LearningPhase[0]);
        jsonWriter.beginObject();
        jsonWriter.name("semester").value(subject.getSemester());
        jsonWriter.name("finalGrade").value(subject.getFinalGrade());
        jsonWriter.name("ectsPoints").value(subject.getEctsPoints());
        jsonWriter.name("weekGoal").value(subject.getWeekGoal());
        jsonWriter.name("subjectName").value(subject.getSubjectName());
        jsonWriter.name("learningPhases");
        jsonWriter.beginArray();
        for(LearningPhase learningPhase:learningPhases){
            gsonBuilder.create().toJson(learningPhase, LearningPhase.class, jsonWriter); //writes LearningPhase
        }
        jsonWriter.endArray();
        jsonWriter.endObject();
    }

    @Override
    public Subject read(JsonReader jsonReader) throws IOException {
        double finalGrade;
        String subjectName;
        int ectsPoint;
        int weekGoal;
        int semester;
        ArrayList<LearningPhase> learningPhases = new ArrayList<LearningPhase>();
        jsonReader.beginObject();
        jsonReader.beginObject();
        jsonReader.nextName();//final Grade
        finalGrade = jsonReader.nextDouble();

        jsonReader.nextName();//extsPoints
        ectsPoint = jsonReader.nextInt();

        jsonReader.nextName();//weekGoal
        weekGoal = jsonReader.nextInt();

        jsonReader.nextName();//subjectName

return null;

    }
}
