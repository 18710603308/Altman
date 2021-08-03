package com.dy_name.controller;

import com.altman.mapper.DemoMapper;
import com.dy_name.mapper.TestDynamicMapper;
import com.dy_name.service.DyNamicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author mzy
 * @date 2021/8/2 21:48
 */
@RestController
public class DemoDyNamicController {

    @Autowired
    private TestDynamicMapper dynamicMapper;
    @Autowired
    private DyNamicServiceImpl dyNamicService;

    @RequestMapping("/demoDynamic")
    public List<Map<String, Object>> demo(){
        return dynamicMapper.query();
    }

    @RequestMapping("/add")
    public void add(){
        dyNamicService.add();
    }

    @RequestMapping("/create")
    public void createDatabase(@RequestParam("name") String namr){
        dynamicMapper.create(namr);
    }
}
