package com.heinsberg.LearningManagerProjekt.BackGround.typeAdapters;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class SemesterTypeAdapter extends TypeAdapter<Semester> {
    @Override
    public void write(JsonWriter jsonWriter, Semester semester) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Subject.class, new SubjectTypeAdapter());
        jsonWriter.beginObject();
        jsonWriter.name("semester").value(semester.getSemester());
        jsonWriter.name("start").value(semester.getTime());
        jsonWriter.name("end").value(semester.getEndDate().getTime());
        Subject[] subjects = semester.getSubjects();
        jsonWriter.name("subjects");
        jsonWriter.beginArray();
        for (Subject subject : subjects) {
            gsonBuilder.create().toJson(subject, Subject.class, jsonWriter); //writes subject
        }
        jsonWriter.endArray();
        jsonWriter.endObject();
    }

    @Override
    public Semester read(JsonReader jsonReader) throws IOException {
        int semester = 0;
        long start = 0, end = 0;
        ArrayList<Subject> subjects = new ArrayList<>();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case ("semester"):
                    semester = jsonReader.nextInt();
                    break;
                case ("start"):
                    start = jsonReader.nextLong();
                    break;
                case ("end"):
                    end = jsonReader.nextLong();
                    break;
                case ("subjects"):
                    subjects = readSubjects(jsonReader);
                    break;

            }
        }
        jsonReader.endObject();
        Date startDate = new Date();
        startDate.setTime(start);
        Date endDate = new Date();
        endDate.setTime(end);
        Semester outPut = new Semester(semester, startDate, endDate);
        for (Subject subject : subjects){
            outPut.addSubject(subject);
            subject.setSemester(outPut);
        }
        outPut.loadLearningPhasesFromSubject();
        return outPut;
    }

    private ArrayList<Subject> readSubjects(JsonReader jsonReader) {
        ArrayList<Subject> outPut = new ArrayList<Subject>();
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Subject.class, new SubjectTypeAdapter());
        Type listType = new TypeToken<ArrayList<Subject>>() {
        }.getType();
        outPut = gsonBuilder.create().fromJson(jsonReader, listType);
        return outPut;
    }
}
