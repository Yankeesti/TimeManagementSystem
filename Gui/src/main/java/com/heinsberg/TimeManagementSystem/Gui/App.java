package com.heinsberg.TimeManagementSystem.Gui;

import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        ViewFactory viewFactory = new ViewFactory(new ContentManager());
        viewFactory.showLoadWindow();
    }





    public static void main(String[] args) {
        launch();
    }

}