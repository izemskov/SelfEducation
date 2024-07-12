package ru.develgame.bookmarks.jsf;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("indexBean")
@ViewScoped
public class IndexBean implements Serializable {
    private int backgroundPositionX = 0;

    public void update() {
        backgroundPositionX -= 10;
    }

    public String getBackgroundPosition() {
        return String.format("background-position: %dpx 0px;", backgroundPositionX);
    }
}
