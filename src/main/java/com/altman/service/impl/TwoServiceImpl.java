package com.altman.service.impl;

import com.altman.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * @author mzy
 * @date 2021/7/20 23:01
 */
@Service
public class TwoServiceImpl implements DemoService {
    @Override
    public void upload(String str) {
        System.out.println(str);
    }
}
