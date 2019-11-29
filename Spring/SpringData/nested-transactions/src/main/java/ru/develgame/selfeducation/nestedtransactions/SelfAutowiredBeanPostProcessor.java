/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.nestedtransactions;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
public class SelfAutowiredBeanPostProcessor implements BeanPostProcessor, Ordered {
    private Map<String, Object> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        if (bean.getClass().getSuperclass() != null) {
            Field[] fields1 = bean.getClass().getSuperclass().getDeclaredFields();
            fields = ArrayUtils.addAll(fields, fields1);
        }

        for (Field field : fields) {
            if (field.isAnnotationPresent(SelfAutowired.class)) {
                map.put(beanName, bean);
                break;
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Object o = map.get(beanName);
        if (o != null) {
            Field[] fields = o.getClass().getDeclaredFields();
            if (bean.getClass().getSuperclass() != null) {
                Field[] fields1 = o.getClass().getSuperclass().getDeclaredFields();
                fields = ArrayUtils.addAll(fields, fields1);
            }

            for (Field field : fields) {
                if (field.isAnnotationPresent(SelfAutowired.class)) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, o, bean);
                    break;
                }
            }
        }

        return bean;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
