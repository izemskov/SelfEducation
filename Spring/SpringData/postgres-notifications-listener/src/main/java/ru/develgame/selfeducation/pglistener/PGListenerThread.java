/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.pglistener;

import org.postgresql.PGNotification;
import org.postgresql.jdbc.PgConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PGListenerThread extends Thread {
    private Connection conn;
    private PgConnection pgConn;

    private boolean stop = false;

    public boolean isStop() {
        synchronized (this) {
            return stop;
        }
    }

    public void setStop(boolean stop) {
        synchronized (this) {
            this.stop = stop;
        }
    }

    public PGListenerThread(Connection conn) throws SQLException {
        this.conn = conn;
        this.pgConn = (PgConnection) conn;
        Statement listenStatement = conn.createStatement();
        listenStatement.execute("LISTEN insert_update_notify");
        listenStatement.close();
    }

    @Override
    public void run() {
        while (!isStop()) {
            try {
                // issue a dummy query to contact the backend
                // and receive any pending notifications.
                Statement selectStatement = conn.createStatement();
                ResultSet rs = selectStatement.executeQuery("SELECT 1");
                rs.close();
                selectStatement.close();

                PGNotification notifications[] = pgConn.getNotifications();

                if (notifications != null) {
                    for (PGNotification pgNotification : notifications) {
                        System.out.println("Got notification: " + pgNotification.getName() +
                                " with payload: " + pgNotification.getParameter());
                    }
                }

                Thread.sleep(500);
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
            catch (InterruptedException ex) {
            }
        }
    }
}
