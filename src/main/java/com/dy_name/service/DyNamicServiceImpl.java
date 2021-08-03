package com.dy_name.service;

import com.dy_name.config.base.DBIdentifier;
import com.dy_name.mapper.TestDynamicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mzy
 * @date 2021/8/2 22:29
 */
@Service
public class DyNamicServiceImpl {

    @Autowired
    private TestDynamicMapper testDynamicMapper;

    @Transactional
    public void add(){
        testDynamicMapper.add();
        testDynamicMapper.add();
        if("flow".contains(DBIdentifier.getJdbcUrl())){
            int i = 1/0;
        }
    }
}
