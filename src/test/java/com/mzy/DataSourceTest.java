package com.mzy;

import com.mzy.mapper.one.OneMapper;
import com.mzy.mapper.two.TwoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @Author Jack Miao
 * @date 2021/1/11 20:55
 * @desc
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceTest {

    @Autowired
    private OneMapper oneMapper;
    @Autowired
    private TwoMapper twoMapper;

    @Test
    public void demo(){
        List<Map<String, Object>> query = oneMapper.query();
        System.out.println(query);

        List<Map<String, Object>> query1 = twoMapper.query();
        System.out.println(query1);
    }
}
