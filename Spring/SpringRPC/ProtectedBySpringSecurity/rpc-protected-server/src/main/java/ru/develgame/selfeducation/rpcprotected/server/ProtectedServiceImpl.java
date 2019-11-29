/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.rpcprotected.server;

import org.springframework.stereotype.Component;
import ru.develgame.selfeducation.rpcprotected.common.ProtectedService;

@Component
public class ProtectedServiceImpl implements ProtectedService {
    @Override
    public void testAccess() {
        System.out.println("Welcome to protected method!");
    }
}
