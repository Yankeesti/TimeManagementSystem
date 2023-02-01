package com.heinsberg.TimeManagementSystem.Gui.view;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.BaseController;
import com.heinsberg.TimeManagementSystem.Gui.controller.LoadWindowController;
import com.heinsberg.TimeManagementSystem.Gui.controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Controls the Windows
 */
public class ViewFactory {
    private String fxmlFolder = "fxmlWindows";
    private ContentManager contentManager;
    private MainWindowController mainWindowController;


    private DialogViewFactory dialogViewFactory;

    public ViewFactory(ContentManager contentManager) {
        this.contentManager = contentManager;
        dialogViewFactory = new DialogViewFactory(contentManager,this);
    }


    public void showLoadWindow() {
        System.out.println("show Load window Called");
        BaseController controller = new LoadWindowController(contentManager, this, fxmlFolder + "/" + "loadWindow");
        initializeStage(controller);
    }

    public void showMainWindow() {
        System.out.println("show Main Window Called");
        mainWindowController = new MainWindowController(contentManager, this, fxmlFolder + "/" + "MainWindow");
        initializeStage(mainWindowController);
    }
    public MainWindowController getMainWindowController(){return mainWindowController;}

    public DialogViewFactory getDialogViewFactory() {
        return dialogViewFactory;
    }



    /**
     * Method to show a file open dialog and allow the user to open a file.
     *
     * @param parentStage      The parent stage of the file save dialog
     * @param extensionFilters A 2D array of extension filters to be added to the file chooser. Each array should contain two elements, the first being the name of the filter and the second being the extension(s) of the filter.
     * @return The file that was selected by the user, or null if no file was selected.
     */
    public File showFileOpener(Stage parentStage, String[][] extensionFilters) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Study");
        for (String[] fileFilter : extensionFilters) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileFilter[0], fileFilter[1]));
        }
        File outPut = fileChooser.showOpenDialog(parentStage);
        return outPut;
    }

    /**
     * Method to show a file save dialog and allow the user to save a file.
     *
     * @param parentStage      The parent stage of the file save dialog
     * @param extensionFilters A 2D array of extension filters to be added to the file chooser. Each array should contain two elements, the first being the name of the filter and the second being the extension(s) of the filter.
     * @return The file that was selected by the user, or null if no file was selected.
     */
    public File showFileSaver(Stage parentStage, String[][] extensionFilters, String preDefinedFileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Study");
        for (String[] fileFilter : extensionFilters) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileFilter[0], fileFilter[1]));
        }
        fileChooser.setInitialFileName(preDefinedFileName);
        File outPut = fileChooser.showSaveDialog(parentStage);
        return outPut;
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
    }

    private void initializeStage(BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName() + ".fxml"));
        fxmlLoader.setController(controller);
        Parent parent;
        try {
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
