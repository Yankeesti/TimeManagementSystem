module com.heinsberg.LearningManagerProjekt.Background {
    requires com.google.gson;
    requires javafx.controls;

    exports com.heinsberg.LearningManagerProjekt.BackGround.study.TimeClasses;
    exports com.heinsberg.LearningManagerProjekt.BackGround.study.subject;
    exports com.heinsberg.LearningManagerProjekt.BackGround.study.dataHandler;
    exports com.heinsberg.LearningManagerProjekt.BackGround.study.typeAdapters;
    exports com.heinsberg.LearningManagerProjekt.BackGround.study.Listeners;
    exports com.heinsberg.LearningManagerProjekt.BackGround.study.Listeners.ChangeEnums;
    exports com.heinsberg.LearningManagerProjekt.BackGround.study;
    exports com.heinsberg.LearningManagerProjekt.BackGround.Project;
    exports com.heinsberg.LearningManagerProjekt.BackGround.abstractClasses;
    exports com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses;
    exports com.heinsberg.LearningManagerProjekt.BackGround;
}