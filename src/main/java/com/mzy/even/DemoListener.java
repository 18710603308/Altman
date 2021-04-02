package com.mzy.even;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author Jack Miao
 * @date 2021/3/31 15:07
 * @desc
 */
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {

    @Override
    public void onApplicationEvent(DemoEvent demoEvent) {
        System.out.println(demoEvent.getMessage());
    }
}
