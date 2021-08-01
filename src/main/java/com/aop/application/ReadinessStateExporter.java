package com.aop.application;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 监听application的可用状态
 * @author wsz
 * @date 2020/8/21  15:44
 */
@Component
public class ReadinessStateExporter {

    @EventListener
    public void onStateChange (AvailabilityChangeEvent<ReadinessState> event) {
        // CORRECT
        // ACCEPTING_TRAFFIC
        // REFUSING_TRAFFIC
        System.out.println(event.getState());
    }
}
