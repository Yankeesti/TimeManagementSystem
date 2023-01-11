package com.heinsberg.LearningManagerProjekt.LearningManagerProjekt.Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import com.heinsberg.LearningManagerProjekt.customcomponents.*;
import com.heinsberg.LearningManagerProjekt.BackGround.Interface ;

public class StudyControllerApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudyControllerApplication.class.getResource("scenes/study-Scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1000,800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        Interface p = new Interface("Test");
    }

    public static void main(String[] args) {
        launch();
    }
}