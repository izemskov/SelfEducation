/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.pglistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.develgame.selfeducation.pglistener.entities.SomeObject;
import ru.develgame.selfeducation.pglistener.repositories.SomeObjectRepository;

import javax.annotation.PreDestroy;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class PGListener {
    @Autowired
    private PGListenerThreadRunner PGListenerThreadRunner;

    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext context = SpringApplication.run(PGListener.class);
        context.getBean(PGListenerThreadRunner.class).runThread();

        SomeObjectRepository someObjectRepository = context.getBean(SomeObjectRepository.class);
        List<SomeObject> all = someObjectRepository.findAll();
        if (all.isEmpty()) {
            SomeObject someObject = new SomeObject();
            someObject.setName("test_" + new Random().nextInt(1000));
            someObjectRepository.save(someObject);
        }
        else {
            SomeObject someObject = all.get(0);
            someObject.setName("test_" + new Random().nextInt(1000));
            someObjectRepository.save(someObject);
        }
    }

    @PreDestroy
    public void onExit() {
        PGListenerThreadRunner.stopThread();
    }
}
