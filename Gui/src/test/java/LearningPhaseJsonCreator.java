import com.google.gson.GsonBuilder;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.TimeManagementSystem.BackGround.study.typeAdapters.LearningPhaseAdapter;

import java.util.Date;

public class LearningPhaseJsonCreator {

    public static void main(String[] args) {
        Date startDate = new Date(122,8,1);
        int timeLearned  = 200;

        Date endDate = new Date();
        endDate.setTime(startDate.getTime());
        endDate.setMinutes(endDate.getMinutes() + timeLearned);

        LearningPhase learningPhase = new LearningPhase(startDate.getTime(),endDate.getTime());
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(LearningPhase.class, new LearningPhaseAdapter());
        System.out.println(gsonBuilder.create().toJson(learningPhase));
    }

}
