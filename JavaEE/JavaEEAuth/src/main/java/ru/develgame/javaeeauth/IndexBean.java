package ru.develgame.javaeeauth;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.security.auth.login.LoginContext;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.core.Context;
import java.io.Serializable;

@Named("indexBean")
@SessionScoped
public class IndexBean implements Serializable {
    private String currentUser;

    @PostConstruct
    public void init() {
        currentUser = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }

    public void throwRuntimeException() {
        throw new RuntimeException("Test message");
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}
