/*
 *
 * This software is copyright protected (c) 2021 by S-Terra CSP
 *
 * Author:              Ilya Zemskov
 * E-mail:              izemskov@s-terra.com
 *
 * Owner:               Ilya Zemskov
 * E-mail:              izemskov@s-terra.com
 *
 * $Header: $
 *
 */
package ru.develgame.loadbalancer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindServiceAppInstancesService {
    @Autowired
    private DiscoveryClient discoveryClient;

    public List<ServiceInstance> findServiceAppInstances() {
        return discoveryClient.getServices()
                .stream()
                .filter(t -> t.equals("serviceapp"))
                .map(t -> discoveryClient.getInstances(t))
                .flatMap(t -> t.stream())
                .collect(Collectors.toList());
    }
}
