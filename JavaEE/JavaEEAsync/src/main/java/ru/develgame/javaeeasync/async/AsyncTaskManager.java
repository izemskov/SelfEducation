package ru.develgame.javaeeasync.async;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.concurrent.ManagedExecutorService;
import java.util.*;

import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class AsyncTaskManager {
    @Resource
    private ManagedExecutorService executorService;

    private Integer defaultDelay = 7;

    private final Map<String, List<AsyncTask>> asyncTasks = new HashMap<>();

    @Lock(WRITE)
    public void startAsyncTask(String page, AsyncTask asyncTask) {
        asyncTask.init(defaultDelay);

        List<AsyncTask> asyncTasksList = asyncTasks.get(page);

        if (asyncTasksList == null) {
            asyncTasksList = new LinkedList<>();
            asyncTasks.put(page, asyncTasksList);
        }

        asyncTasksList.add(asyncTask);
    }

    @Lock(READ)
    public List<AsyncTask> getAsyncTasks(String page) {
        List<AsyncTask> asyncTasksList = this.asyncTasks.get(page);
        if (asyncTasksList == null)
            return new LinkedList<>();
        return new LinkedList<>(asyncTasksList);
    }

    @Lock(READ)
    public void undoTask(String page, AsyncTask undoTask) {
        List<AsyncTask> asyncTasksList = this.asyncTasks.get(page);
        if (asyncTasksList != null) {
            for (AsyncTask asyncTask : asyncTasksList) {
                if (asyncTask.equals(undoTask) && asyncTask.getAsyncTaskStatus() == AsyncTaskStatus.IDLE)
                {
                    asyncTask.setAsyncTaskStatus(AsyncTaskStatus.FINISH);
                }
            }
        }
    }

    @Lock(READ)
    @Schedule(hour = "*", minute = "*", second = "*/1", persistent = false)
    public void refreshAsyncTaskList() {
        for (List<AsyncTask> asyncTasksList : asyncTasks.values()) {
            Iterator<AsyncTask> iterator = asyncTasksList.iterator();
            while (iterator.hasNext()) {
                AsyncTask asyncTask = iterator.next();
                if (asyncTask.getAsyncTaskStatus() == AsyncTaskStatus.IDLE) {
                    asyncTask.decreaseTicksCount();
                    if (asyncTask.getLeftTicksCount() == 0) {
                        asyncTask.setAsyncTaskStatus(AsyncTaskStatus.START);
                    }
                }
                else if (asyncTask.getAsyncTaskStatus() == AsyncTaskStatus.START) {
                    asyncTask.setAsyncTaskStatus(AsyncTaskStatus.IN_PROGRESS);
                    executorService.submit(asyncTask);
                }
            }
        }
    }

    @Lock(WRITE)
    @Schedule(hour = "*", minute = "*", second = "*/10", persistent = false)
    public void removeFinishedTasks() {
        for (List<AsyncTask> asyncTasksList : asyncTasks.values()) {
            Iterator<AsyncTask> iterator = asyncTasksList.iterator();
            while (iterator.hasNext()) {
                AsyncTask asyncTask = iterator.next();
                if (asyncTask.getAsyncTaskStatus() == AsyncTaskStatus.FINISH) {
                    iterator.remove();
                }
            }
        }
    }
}
