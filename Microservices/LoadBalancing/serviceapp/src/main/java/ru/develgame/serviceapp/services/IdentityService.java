/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */
package ru.develgame.serviceapp.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdentityService {
    private String guid = UUID.randomUUID().toString();

    public String getGuid() {
        return guid;
    }
}
