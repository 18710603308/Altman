package com.altman.service.impl;

import com.altman.mapper.DemoMapper;
import com.altman.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mzy
 * @date 2021/7/20 22:59
 */
@Service
public class OneServiceImpl implements DemoService {
    @Autowired
    private DemoMapper demoMapper;
    @Override
    public void upload(String str, Integer a) {
        demoMapper.demo();
        System.out.println(str);
    }
}
