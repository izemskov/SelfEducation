/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */
package ru.develgame.loadbalancer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import ru.develgame.loadbalancer.services.FindServiceAppInstancesService;

@SpringBootApplication
@EnableDiscoveryClient
public class LoadbalancerApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(LoadbalancerApplication.class, args);
		context.getBean(FindServiceAppInstancesService.class).findServiceAppInstances();
	}
}
