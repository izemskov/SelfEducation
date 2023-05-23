package ru.develgame.javaeeehandling;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("indexBean")
@SessionScoped
public class IndexBean implements Serializable {
    public void throwRuntimeException() {
        throw new RuntimeException("Test message");
    }
}
