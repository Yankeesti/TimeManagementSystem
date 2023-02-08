module com.heinsberg.TimeManagementSystem.Background {
    requires com.google.gson;
    requires javafx.controls;

    exports com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses;
    exports com.heinsberg.TimeManagementSystem.BackGround.study.subject;
    exports com.heinsberg.TimeManagementSystem.BackGround.study.dataHandler;
    exports com.heinsberg.TimeManagementSystem.BackGround.study.typeAdapters;
    exports com.heinsberg.TimeManagementSystem.BackGround.study.Listeners;
    exports com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.ChangeEnums;
    exports com.heinsberg.TimeManagementSystem.BackGround.study;
    exports com.heinsberg.TimeManagementSystem.BackGround.Project;
    exports com.heinsberg.TimeManagementSystem.BackGround.abstractClasses;
    exports com.heinsberg.TimeManagementSystem.BackGround.TimeClasses;
    exports com.heinsberg.TimeManagementSystem.BackGround;
    exports com.heinsberg.TimeManagementSystem.BackGround.Listeners.ChangeEnums;
    exports com.heinsberg.TimeManagementSystem.BackGround.Listeners;
    exports com.heinsberg.TimeManagementSystem.BackGround.TypeAdapter;
}