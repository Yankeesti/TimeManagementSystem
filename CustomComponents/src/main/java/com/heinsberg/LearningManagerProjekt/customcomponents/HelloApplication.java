package com.heinsberg.LearningManagerProjekt.customcomponents;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application implements EventHandler<ActionEvent> {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

//        Group noode = new Group();
//        SemesterChooser semesterChooser = new SemesterChooser();
//        semesterChooser.setSemesters(5,"TechnischeInformatik",this);
//        noode.getChildren().add(semesterChooser);
//        Scene scene = new Scene(noode, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println(((Button)event.getSource()).getText());
    }
}