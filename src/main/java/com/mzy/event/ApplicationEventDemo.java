package com.mzy.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Author Jack Miao
 * @date 2021/1/29 16:35
 * @desc
 */
public class ApplicationEventDemo extends ApplicationEvent {

    private String str;

    public ApplicationEventDemo(Object source) {
        super(source);
    }

    public ApplicationEventDemo(Object source, String str){
        super(source);
        this.str = str;
    }

    public String getStr(){
        return this.str;
    }
}
