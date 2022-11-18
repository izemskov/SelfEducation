package ru.develgame.springasync;

import java.util.Random;
import java.util.UUID;

public class Worker {
    private String guid = UUID.randomUUID().toString();

    public Double job() {
        long start = System.nanoTime();

        double a = new Random().nextDouble();

        for (long i = 0; i < 100000000; i++) {
            a = a + Math.tan(a);
        }

        long finish = System.nanoTime();

        System.out.println(guid + " --- " + (finish - start) / 1000000000.0d + " sec");

        return a;
    }
}
