/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */
package ru.develgame.serviceapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.serviceapp.services.IdentityService;

@RestController
public class MainController {
    @Autowired
    private IdentityService identityService;

    @GetMapping("/identity")
    public String identity() {
        return identityService.getGuid();
    }
}
