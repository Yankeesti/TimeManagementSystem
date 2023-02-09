package com.heinsberg.TimeManagementSystem.Gui.view;

import com.heinsberg.TimeManagementSystem.BackGround.TimeManagementSystemObjects;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.BaseController;
import com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Project.ProjectCreateController;
import com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Project.ProjectEditorController;
import com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Subject.SubjectChooserController;
import com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Subject.SubjectCreatorController;
import com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Subject.SubjectEditController;
import com.heinsberg.TimeManagementSystem.BackGround.Project.Project;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.IOException;
import java.util.Optional;

/**
 * A Class wich handles the Dialog Windows of the Application
 */
public class DialogViewFactory {
    private ContentManager contentManager;
    private ViewFactory viewFactory;


    public DialogViewFactory(ContentManager contentManager, ViewFactory viewFactory) {
        this.contentManager = contentManager;
        this.viewFactory = viewFactory;
    }

    public Subject showSubjectCreator(Semester semester) {
        System.out.println("show Subject Creator");
        SubjectCreatorController controller = new SubjectCreatorController(contentManager, viewFactory, "dialogBoxes/subjectDialogBox", semester);
        Optional<ButtonType> buttonClicked = showDialog(controller, "Erstelle Fach im " + semester.getSemester() + ". Semester");
        if (buttonClicked.get() == ButtonType.OK) {
            return controller.getSelectedStudy();
        } else {
            return null;
        }
    }

    public Project showProjectCreator() {
        System.out.println("Show Project Creator");
        ProjectCreateController controller = new ProjectCreateController(contentManager, viewFactory, "dialogBoxes/projectDialogBox");
        Optional<ButtonType> buttonClicked = showDialog(controller, "Erstelle Project");
        if (buttonClicked.get() == ButtonType.OK) {
            return controller.getCreatedProject();
        } else {
            return null;
        }
    }


    /**
     * Shows a Dialog Pane where the user can edit the properties of subject
     * or delete it
     *
     * @param subject
     */
    public void showSubjectEditor(Subject subject) {
        System.out.println("show Subject Editor");
        SubjectEditController controller = new SubjectEditController(contentManager, viewFactory, "dialogBoxes/subjectDialogBox", subject);
        Optional<ButtonType> buttonClicked = showDialog(controller, "Edit Subject");
        if (buttonClicked.get() == ButtonType.OK) {
            controller.submitChanges();
        } else if (buttonClicked.get() == controller.getDeleteButton()) {
            deleteTimeSpentContainer(subject);
        }
    }


    public void showProjectEditor(Project project) {
        System.out.println("Show Project Editor");
        ProjectEditorController controller = new ProjectEditorController(contentManager, viewFactory, "dialogBoxes/projectDialogBox", project);
        Optional<ButtonType> buttonClicked = showDialog(controller, "Edit Subject");
        if (buttonClicked.get() == ButtonType.OK) {
            controller.submitChanges();
        } else if (buttonClicked.get() == controller.getDeleteButton()) {
            deleteTimeSpentContainer(project);
        }
    }

    /**
     * Opens a Dialog Window where a subject from subjects can be picked
     *
     * @param subjects - subjects to pick from
     * @return picked Subject
     */
    public TimeSpentContainer showTimeSpentContainerChooser(ObservableList<TimeSpentContainer> timeSpentContainers) {
        System.out.println("show Subject Chooser");
        SubjectChooserController controller = new SubjectChooserController(contentManager, viewFactory, "dialogBoxes/selectSubjectDialogBox", timeSpentContainers);
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

    public void showDeleteLearningPhaseDialog(LearningPhase learningPhase) {
        Alert learningPhaseDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        learningPhaseDeleteAlert.setTitle("Lern Phase Löschen");
        learningPhaseDeleteAlert.setHeaderText("Sind Sie sicher das Sie die Lern Phase löschen möchten?");


        Optional<ButtonType> result = learningPhaseDeleteAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            contentManager.deleteLearningPhase(learningPhase);
        }
    }


    /**
     * Opens the Delete Dialog when the user selects ok the object get Deleted and Main Window shows the standard Information
     * @param objectToDelete
     */
    public void deleteObject(Object objectToDelete) {
        //What object should be deleted out of TimeManagements System
        if (objectToDelete instanceof Subject || objectToDelete instanceof Project)
            deleteTimeSpentContainer((TimeSpentContainer) objectToDelete);
        else if (objectToDelete instanceof Semester) {
            deleteSemester((Semester) objectToDelete);
        } else if (objectToDelete instanceof Study) {
            deleteStudy((Study) objectToDelete);
        }
    }

    /**
     * Opens a new Dialog pane where the user is asked
     * if he really wants to delete the Study
     * when he selects Ok the Study gets deleted and main Window shows the Standard Informationpane
     * @param studyToDelete - object tp be deleted
     */
    public void deleteStudy(Study studyToDelete) {
        Alert deletObjectAlert = new Alert(Alert.AlertType.CONFIRMATION);
        TimeManagementSystemObjects sortOfObject;
        deletObjectAlert.setTitle(("Delete Study"));
        deletObjectAlert.setHeaderText("Are you sure you want to delete " + ((Study) studyToDelete).getName() + " and all its Smesters and Subjects?");
        Optional<ButtonType> result = deletObjectAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            contentManager.deleteStudy(studyToDelete);
            //Specifie wih node should be shown when closed
            viewFactory.getMainWindowController().showStandardInformation();//shows study Information Pane
        }
    }

    /**
     * Opens a new Dialog pane where the user is asked
     * if he really wants to delete the Semester
     * when he selects Ok the Semester gets deleted and main Window shows the Standard Informationpane
     *
     * @param semesterToDelete - object tp be deleted
     */
    public void deleteSemester(Semester semesterToDelete) {
        Alert deletObjectAlert = new Alert(Alert.AlertType.CONFIRMATION);
        TimeManagementSystemObjects sortOfObject;
        deletObjectAlert.setTitle("Delete Semester");
        deletObjectAlert.setHeaderText("Are you sure you want to delete the " + ((Semester) semesterToDelete).getSemester() + ". Semester and all its Subjects ?");
        Optional<ButtonType> result = deletObjectAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            contentManager.deleteSemester(semesterToDelete);
            //Specifie wih node should be shown when closed
            viewFactory.getMainWindowController().showStandardInformation();//shows study Information Pane
        }
    }

    /**
     * Opens a new Dialog pane where the user is asked
     * if he really wants to delete the Subject/Project
     * when he selects Ok the Subject gets deleted and main Window shows the Standard Informationpane
     *
     * @param timeSpentContainer - object tp be deleted
     */
    public void deleteTimeSpentContainer(TimeSpentContainer timeSpentContainer) {
        Alert deletObjectAlert = new Alert(Alert.AlertType.CONFIRMATION);
        TimeManagementSystemObjects sortOfObject;
        if (timeSpentContainer instanceof Subject) {
            deletObjectAlert.setTitle("Delete Subject");
            deletObjectAlert.setHeaderText("Are you sure you want to delete: " + ((Subject) timeSpentContainer).getName() + " ?");
        } else if (timeSpentContainer instanceof Project) {
            deletObjectAlert.setTitle("Delete Project");
            deletObjectAlert.setHeaderText("Are you sure you want to delete: " + ((Project) timeSpentContainer).getName() + " ?");
        }
        Optional<ButtonType> result = deletObjectAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            contentManager.deleteTimeSpentContainer(timeSpentContainer);
            //Specifie wih node should be shown when closed
            viewFactory.getMainWindowController().showStandardInformation();//shows study Information Pane

        }
    }


}
