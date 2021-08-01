package com.aop.application;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.context.event.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * 事件监听
 * spring.factories中指定监听器对象
 * org.springframework.context.ApplicationListener=com.aop.application.EventsListener
 * @author wsz
 * @date 2020/8/21  16:08
 */
public class EventsListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ApplicationStartingEvent) {
            System.out.println("ApplicationStartingEvent");
            return;
        }

        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            System.out.println("ApplicationEnvironmentPreparedEvent");
            return;
        }

        if (event instanceof ApplicationContextInitializedEvent) {
            System.out.println("ApplicationContextInitializedEvent");
            return;
        }

        if (event instanceof ApplicationPreparedEvent) {
            System.out.println("ApplicationPreparedEvent");
            return;
        }

        if (event instanceof AvailabilityChangeEvent) {
            System.out.println("AvailabilityChangeEvent");
            return;
        }

        if (event instanceof ApplicationReadyEvent) {
            System.out.println("ApplicationReadyEvent");
            return;
        }

        if (event instanceof AvailabilityChangeEvent) {
            System.out.println("AvailabilityChangeEvent");
            return;
        }

        if (event instanceof ApplicationFailedEvent) {
            System.out.println("ApplicationFailedEvent");
            return;
        }
    }
}
