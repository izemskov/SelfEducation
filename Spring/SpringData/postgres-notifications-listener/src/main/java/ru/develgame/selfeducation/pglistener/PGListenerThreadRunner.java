/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.pglistener;

import org.postgresql.jdbc.PgConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class PGListenerThreadRunner {
    private PGListenerThread PGListenerThread;

    @Autowired
    private DataSource dataSource;

    public void runThread() throws SQLException {
        if (PGListenerThread == null) {
            Connection connection = dataSource.getConnection();
            PgConnection unwrapConnection = connection.unwrap(PgConnection.class);

            PGListenerThread = new PGListenerThread(unwrapConnection);
            PGListenerThread.start();
        }
    }

    public void stopThread() {
        if (PGListenerThread != null) {
            PGListenerThread.setStop(true);

            try {
                PGListenerThread.join();
            }
            catch (InterruptedException e) {
            }

            PGListenerThread = null;
        }
    }
}
