package com.heinsberg.TimeManagementSystem.Gui.controller;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;

public abstract class BaseController {
    protected ContentManager contentManager;
    protected ViewFactory viewFactory;
    protected String fxmlName;

    public BaseController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        this.contentManager = contentManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName(){return fxmlName;}

}
