package ru.develgame.javaeeit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@Named
@ApplicationScoped
public class MyBean implements Serializable {
    private String guid = UUID.randomUUID().toString();

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
