/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */
package ru.develgame.loadbalancer;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.List;

public class DemoServiceInstanceListSuppler implements ServiceInstanceListSupplier {
    private String serviceId;
    private List<ServiceInstance> serviceInstances;

    public DemoServiceInstanceListSuppler(String serviceId, List<ServiceInstance> serviceInstances) {
        this.serviceId = serviceId;
        this.serviceInstances = serviceInstances;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(serviceInstances);
    }
}
