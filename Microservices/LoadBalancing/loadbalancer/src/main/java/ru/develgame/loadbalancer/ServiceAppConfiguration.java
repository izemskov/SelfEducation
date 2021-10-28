/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */
package ru.develgame.loadbalancer;

import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ServiceAppConfiguration {
    @Bean
    @Primary
    public ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new DemoServiceInstanceListSuppler("serviceapp");
    }
}
