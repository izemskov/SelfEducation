package ru.develgame.javaeeasync;

import ru.develgame.javaeeasync.async.AsyncTask;
import ru.develgame.javaeeasync.async.AsyncTaskManager;
import ru.develgame.javaeeasync.async.AsyncTaskStatus;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@Named("demoAsyncTasks")
@ViewScoped
public class DemoAsyncTasksBean implements Serializable {
    @EJB
    private AsyncTaskManager asyncTaskManager;

    private AsyncBean asyncBean;

    private String page;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        page = ((HttpServletRequest) context.getExternalContext().getRequest()).getRequestURI();
    }
    
    public int getRandomValue() {
        asyncBean = CDI.current().select(AsyncBean.class).get();
        return asyncBean.getRandomValue();
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(severity, summary, detail));
    }

    public void showInfo() {
        addMessage(FacesMessage.SEVERITY_INFO, "Info Message", "Refreshed");
    }

    public AsyncBean getAsyncBean() {
        return asyncBean;
    }

    public void startAsyncTask() {
        AsyncBean asyncBean = CDI.current().select(AsyncBean.class).get();
        asyncTaskManager.startAsyncTask(page, asyncBean);
    }

    public List<AsyncTask> getAsyncTasks() {
        return asyncTaskManager.getAsyncTasks(page);
    }

    public String getAsyncTaskInfo(AsyncBean asyncTask) {
        if (asyncTask.getAsyncTaskStatus() == AsyncTaskStatus.IN_PROGRESS)
            return "Random value: " + asyncTask.getRandomValue();

        return "";
    }

    public void undoTask(AsyncTask asyncTask) {
         asyncTaskManager.undoTask(page, asyncTask);
    }
}
