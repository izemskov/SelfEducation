/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.nestedtransactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.develgame.selfeducation.nestedtransactions.entities.SomeObject;
import ru.develgame.selfeducation.nestedtransactions.repositories.SomeObjectRepository;

@Component
public class DoSomeTransactions {
    @Autowired
    private SomeObjectRepository someObjectRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requireNewTransaction() {
        SomeObject someObject = new SomeObject();
        someObject.setName("test1");
        someObjectRepository.save(someObject);

        throw new RuntimeException("Rollback transaction");
    }

    /**
     * In this function, a new transaction will not start when
     * the requireNewTransaction function is called.
     */
    @Transactional
    public void doTransaction1() {
        SomeObject someObject = new SomeObject();
        someObject.setName("test");
        someObjectRepository.save(someObject);

        try {
            requireNewTransaction();
        }
        catch (RuntimeException ex) {}
    }
}
