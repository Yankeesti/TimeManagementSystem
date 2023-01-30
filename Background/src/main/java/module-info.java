module com.heinsberg.LearningManagerProjekt.Background {
    requires com.google.gson;
    requires javafx.controls;

    exports com.heinsberg.LearningManagerProjekt.BackGround;
    exports com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses;
    exports com.heinsberg.LearningManagerProjekt.BackGround.subject;
    exports com.heinsberg.LearningManagerProjekt.BackGround.dataHandler;
    exports com.heinsberg.LearningManagerProjekt.BackGround.typeAdapters;
    exports com.heinsberg.LearningManagerProjekt.BackGround.Listeners;
    exports com.heinsberg.LearningManagerProjekt.BackGround.Listeners.ChangeEnums;
}