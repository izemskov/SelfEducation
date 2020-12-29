/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.nestedtransactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;
import ru.develgame.selfeducation.nestedtransactions.entities.SomeObject;
import ru.develgame.selfeducation.nestedtransactions.repositories.SomeObjectRepository;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class NestedTransactions {
    @Autowired
    private DoSomeTransactions doSomeTransactions;

    @Autowired
    private SomeObjectRepository someObjectRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager bean = new JpaTransactionManager(entityManagerFactory);
        return bean ;
    }

    @Bean
    public AnnotationTransactionAspect annotationTransactionAspect() {
        AnnotationTransactionAspect bean = AnnotationTransactionAspect.aspectOf();
        bean.setTransactionManager(transactionManager());
        return bean;
    }

    public static void main(String[] args) {
        SpringApplication.run(NestedTransactions.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            try {
                doSomeTransactions.doTransaction1();
            }
            catch (RuntimeException ex) {}

            System.out.println("=== Results ===");
            List<SomeObject> all = someObjectRepository.findAll();
            all.forEach(e -> System.out.println(e.getName()));
        };
    }
}
