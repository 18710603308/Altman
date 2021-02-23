package com.mzy.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author Jack Miao
 * @date 2021/1/29 16:40
 * @desc
 */
@Component
public class ApplicationListenerDemo implements ApplicationListener<ApplicationEventDemo> {

    private static final Logger  LOGGER = LoggerFactory.getLogger(ApplicationListenerDemo.class);
    @Override
    public void onApplicationEvent(ApplicationEventDemo applicationEventDemo) {
        LOGGER.debug("event str :" + applicationEventDemo.getStr());
    }
}
