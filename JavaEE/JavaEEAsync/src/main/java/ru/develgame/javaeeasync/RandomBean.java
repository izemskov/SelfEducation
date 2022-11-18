package ru.develgame.javaeeasync;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Random;

@SessionScoped
@Named("randomBean")
public class RandomBean implements Serializable {
    private Random random = new Random();

    public int getRandomValue() {
        return random.nextInt(100);
    }
}
