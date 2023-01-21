package com.heinsberg.LearningManager.Gui.view;

import com.heinsberg.LearningManager.Gui.App;
import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.controller.LoadWindowController;
import com.heinsberg.LearningManager.Gui.controller.MainWindowController;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controls the Windows
 */
public class ViewFactory {
    private String fxmlFolder ="fxmlWindows";
    private StudyManager studyManager;
    public ViewFactory(StudyManager studyManager) {
        this.studyManager = studyManager;
    }


    public void showLoadWindow() {
        System.out.println("show Load window Called");
        BaseController controller = new LoadWindowController(studyManager,this,"loadWindow");
        initializeStage(controller);
    }

    public void showMainWindow(){
        System.out.println("show Main Window Called");
        MainWindowController controller = new MainWindowController(studyManager,this,"MainWindow");
        initializeStage(controller);
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
    }

    private void initializeStage(BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFolder+"/"+controller.getFxmlName()+".fxml"));
        fxmlLoader.setController(controller);
        Parent parent;
        try{
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


}
