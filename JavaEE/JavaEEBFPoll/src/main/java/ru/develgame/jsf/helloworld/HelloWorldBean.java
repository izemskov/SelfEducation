package ru.develgame.jsf.helloworld;

import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class HelloWorldBean implements Serializable {
    private int counter;
    private boolean stop = false;

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public int getCounter() {
        counter++;
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void listener(ActionEvent event) {
        counter++;
    }
}
