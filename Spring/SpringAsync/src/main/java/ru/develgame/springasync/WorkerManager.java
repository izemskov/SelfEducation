package ru.develgame.springasync;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;

public abstract class WorkerManager {
    @Async("threadPoolTestAsyncExecutor")
    public Future<Void> calculate() {
        getNewWorker().job();

        return new AsyncResult<>(null);
    }

    protected abstract Worker getNewWorker();
}
