/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.rpcprotected.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import ru.develgame.selfeducation.rpcprotected.common.ProtectedService;

@SpringBootApplication
public class RPCProtectedServer {
    @Autowired
    private ProtectedService protectedService;

    @Bean(name = "/protectedService")
    public HttpInvokerServiceExporter httpInvokerServiceExporter() {
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(protectedService);
        exporter.setServiceInterface(ProtectedService.class);
        return  exporter;
    }

    public static void main(String[] args) {
        SpringApplication.run(RPCProtectedServer.class);
    }
}
