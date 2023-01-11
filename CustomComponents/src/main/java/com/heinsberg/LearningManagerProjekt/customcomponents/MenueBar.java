package com.heinsberg.LearningManagerProjekt.customcomponents;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenueBar extends MenuBar {
    public MenueBar(){
        super(
              new Menu("File",null,new MenuItem[]{new MenuItem("Save"),new MenuItem("Save as..."),new MenuItem("load")})
        );

    }
}
