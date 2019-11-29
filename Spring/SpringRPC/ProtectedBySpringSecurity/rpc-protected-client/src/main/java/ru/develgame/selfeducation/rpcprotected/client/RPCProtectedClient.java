/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.rpcprotected.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.remoting.httpinvoker.AuthenticationSimpleHttpInvokerRequestExecutor;
import ru.develgame.selfeducation.rpcprotected.common.ProtectedService;

@SpringBootApplication
public class RPCProtectedClient {
    @Bean
    public AuthenticationSimpleHttpInvokerRequestExecutor authenticationSimpleHttpInvokerRequestExecutor() {
        return new AuthenticationSimpleHttpInvokerRequestExecutor();
    }

    @Bean
    public HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl("http://127.0.0.1:8080/protectedService");
        invoker.setServiceInterface(ProtectedService.class);
        invoker.setHttpInvokerRequestExecutor(authenticationSimpleHttpInvokerRequestExecutor());
        return invoker;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RPCProtectedClient.class);

        TestAccess testAccess = context.getBean(TestAccess.class);

        System.out.println("Test without authentication data");
        testAccess.testAccess();

        String user = "test";
        String pw = "test";
        SecurityContextImpl sc = new SecurityContextImpl();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, pw);

        sc.setAuthentication(auth);
        SecurityContextHolder.setContext(sc);

        System.out.println("Test with authentication data");
        testAccess.testAccess();
    }
}
