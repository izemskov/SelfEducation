/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.rpcprotected.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Component;
import ru.develgame.selfeducation.rpcprotected.common.ProtectedService;

@Component
public class TestAccess {
    @Autowired
    private ProtectedService protectedService;

    public void testAccess() {
        try {
            protectedService.testAccess();
            System.out.println("Access granted");
        }
        catch (RemoteAccessException ex) {
            System.out.println("Access denied");
        }
    }
}
