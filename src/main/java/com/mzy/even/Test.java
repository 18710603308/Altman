package com.mzy.even;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jack Miao
 * @date 2021/3/31 15:09
 * @desc
 */
@Controller
public class Test {

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("/even")
    @ResponseBody
    public void  publish(){
        applicationContext.publishEvent(new DemoEvent(this, "demo"));
    }
}
