package com.heinsberg.LearningManagerProjekt.BackGround.typeAdapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;

import java.io.IOException;
import java.util.Date;

public class LearningPhaseAdapter extends TypeAdapter<LearningPhase> {
    @Override
    public void write(JsonWriter jsonWriter, LearningPhase learningPhase) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("startDate").value(learningPhase.getTime());
        if(learningPhase.getEndDate() == null)
            jsonWriter.name("endDate").value(-1);
        else{
        jsonWriter.name("endDate").value(learningPhase.getEndDate().getTime());}
        jsonWriter.endObject();

    }

    @Override
    public LearningPhase read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        System.out.println(jsonReader.toString());
        System.out.println(jsonReader.nextName());
        long startDate = jsonReader.nextLong();
        System.out.println(jsonReader.nextName());
        long endDate = jsonReader.nextLong();
        jsonReader.endObject();

        LearningPhase outPut = new LearningPhase(null);
        outPut.setTime(startDate);
        outPut.setEndTime(endDate);
        //System.out.println(outPut.getEndDate().getTime());
        return outPut;
//        jsonReader.beginObject();
//        jsonReader.nextName();
//        long startDate = jsonReader.nextLong();
//        jsonReader.nextName();
//        long endDate = jsonReader.nextLong();
//        jsonReader.endObject();
//        LearningPhase outPut = new LearningPhase(null);
//        outPut.setTime(startDate);
//        if(endDate >= 0){
//            outPut.setEndTime(endDate);
//        }
//        return outPut;
    }
}
