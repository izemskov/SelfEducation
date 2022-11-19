package ru.develgame.javaeeasync.async;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AsyncTask implements Runnable {
    private final String uuid = UUID.randomUUID().toString();

    protected AtomicReference<AsyncTaskStatus> asyncTaskStatus = new AtomicReference<>(AsyncTaskStatus.PREPARE);

    protected int delay;

    private AtomicInteger leftTicksCount;

    public void init(int defaultDelay) {
        this.delay = defaultDelay;
        leftTicksCount = new AtomicInteger(defaultDelay);
        setAsyncTaskStatus(AsyncTaskStatus.IDLE);
    }

    public String getUuid() {
        return uuid;
    }

    public AsyncTaskStatus getAsyncTaskStatus() {
        return asyncTaskStatus.get();
    }

    protected void setAsyncTaskStatus(AsyncTaskStatus asyncTaskStatus) {
        while (!this.asyncTaskStatus.compareAndSet(this.asyncTaskStatus.get(), asyncTaskStatus)) {}
    }

    public int getLeftTicksCount() {
        return leftTicksCount.get();
    }

    public void decreaseTicksCount() {
        if (leftTicksCount.get() > 0)
            leftTicksCount.set(leftTicksCount.get() - 1);
    }

    @Override
    public void run() {
        setAsyncTaskStatus(AsyncTaskStatus.FINISH);
    }
}
