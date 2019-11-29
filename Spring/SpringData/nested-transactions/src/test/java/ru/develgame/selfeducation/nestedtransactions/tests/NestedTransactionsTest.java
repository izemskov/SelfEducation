/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.nestedtransactions.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.develgame.selfeducation.nestedtransactions.DoSomeTransactions;
import ru.develgame.selfeducation.nestedtransactions.entities.SomeObject;
import ru.develgame.selfeducation.nestedtransactions.repositories.SomeObjectRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(NestedTransactionsTestConfiguration.class)
public class NestedTransactionsTest {
    @Autowired
    private SomeObjectRepository someObjectRepository;

    @Autowired
    private DoSomeTransactions doSomeTransactions;

    @Test
    @Transactional
    public void test1() {
        doSomeTransactions.doTransaction1();

        List<SomeObject> all = someObjectRepository.findAll();
        Assert.assertNotNull(all);
        Assert.assertEquals(all.size(), 2);
        Assert.assertEquals(all.get(0).getName(), "test");
        Assert.assertEquals(all.get(1).getName(), "test1");
    }

    @Test
    @Transactional
    public void test2() {
        doSomeTransactions.doTransaction2();

        List<SomeObject> all = someObjectRepository.findAll();
        Assert.assertNotNull(all);
        Assert.assertEquals(all.size(), 1);
        Assert.assertEquals(all.get(0).getName(), "test");
    }
}
