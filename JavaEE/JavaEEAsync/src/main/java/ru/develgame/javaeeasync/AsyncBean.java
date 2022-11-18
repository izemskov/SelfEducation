package ru.develgame.javaeeasync;

import ru.develgame.javaeeasync.async.AsyncTask;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.Serializable;

@Dependent
public class AsyncBean extends AsyncTask implements Serializable {
    @Inject
    private RandomBean randomBean;

    private int randomValue;

    @PostConstruct
    public void init() {
        randomValue = randomBean.getRandomValue();
    }

    public int getRandomValue() {
        return randomValue;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }

        super.run();
    }
}
