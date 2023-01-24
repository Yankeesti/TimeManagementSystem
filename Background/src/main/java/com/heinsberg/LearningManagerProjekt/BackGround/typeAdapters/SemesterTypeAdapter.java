package com.heinsberg.LearningManagerProjekt.BackGround.typeAdapters;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;

import java.io.IOException;

public class SemesterTypeAdapter extends TypeAdapter<Semester> {
    @Override
    public void write(JsonWriter jsonWriter, Semester semester) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Subject.class,new SubjectTypeAdapter());
        jsonWriter.beginObject();
        jsonWriter.name("semester").value(semester.getSemester());
        jsonWriter.name("start").value(semester.getTime());
        jsonWriter.name("end").value(semester.getEndDate().getTime());
        Subject[] subjects = semester.getSubjects();
        jsonWriter.name("subjects");
        jsonWriter.beginArray();
        for(Subject subject: subjects){
            gsonBuilder.create().toJson(subject, Subject.class, jsonWriter); //writes subject
        }
        jsonWriter.endArray();
        jsonWriter.endObject();
    }

    @Override
    public Semester read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
