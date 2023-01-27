package com.heinsberg.LearningManager.Gui.view;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.view.DialogPaneControllers.SubjectChooserController;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.IOException;
import java.util.Optional;

/**
 * A Class wich handles the Dialog Windows of the Application
 */
public class DialogViewFactory {
    private StudyManager studyManager;
    private ViewFactory viewFactory;

    public DialogViewFactory(StudyManager studyManager, ViewFactory viewFactory) {
        this.studyManager = studyManager;
        this.viewFactory = viewFactory;
    }

    /**
     * Opens a Dialog Window where a subject from subjects can be picked
     *
     * @param subjects - subjects to pick from
     * @return picked Subject
     */
    public Subject showSubjectChooser(ObservableList subjects) {
        System.out.println("show Subject Chooser");
        SubjectChooserController controller = new SubjectChooserController(studyManager, viewFactory, "dialogBoxes/selectSubjectDialogBox",subjects);
        Optional<ButtonType> buttonClicked = showDialog(controller, "Select Subject");
        if (buttonClicked.get() == ButtonType.OK) {
            return controller.getSelected();
        } else if (buttonClicked.get() == ButtonType.CANCEL) {
            return null;
        }
        return null;
    }

    /**
     * shows a Dialog with the given Dialog Pane and Title,
     * while dialog is shown the rest of the application can't be used
     *
     * @param controller - controller for a DialogPane
     * @param title
     */
    private Optional<ButtonType> showDialog(BaseController controller, String title) {
        DialogPane dialogPane = (DialogPane) getNode(controller);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.setTitle(title);
        return dialog.showAndWait();
    }

    private Node getNode(BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName() + ".fxml"));
        fxmlLoader.setController(controller);
        Node node;
        try {
            node = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return node;
    }
}
