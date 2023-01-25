package com.heinsberg.LearningManagerProjekt.BackGround.typeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;

import java.io.IOException;

public class LearningPhaseAdapter extends TypeAdapter<LearningPhase> {
    @Override
    public void write(JsonWriter jsonWriter, LearningPhase learningPhase) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("startDate").value(learningPhase.getTime());
        if (learningPhase.getEndDate() == null)
            jsonWriter.name("endDate").value(Long.MIN_VALUE);
        else {
            jsonWriter.name("endDate").value(learningPhase.getEndDate().getTime());
        }
        jsonWriter.endObject();

    }

    @Override
    public LearningPhase read(JsonReader jsonReader) throws IOException {
        long start = 0, end = 0;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "startDate":
                    start = jsonReader.nextLong();
                    break;
                case "endDate":
                    end = jsonReader.nextLong();
                    break;
            }
        }
        jsonReader.endObject();
        return new LearningPhase(start, end);
    }
}
