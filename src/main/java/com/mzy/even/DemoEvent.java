package com.mzy.even;

import org.springframework.context.ApplicationEvent;

/**
 * @author Jack Miao
 * @date 2021/3/31 15:04
 * @desc
 */
public class DemoEvent extends ApplicationEvent {

    private String message;

    public DemoEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
