package ru.develgame.javaeeit;

import javax.ejb.Singleton;
import java.util.UUID;

@Singleton
public class MyEJBBean {
    private String guid = UUID.randomUUID().toString();

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
