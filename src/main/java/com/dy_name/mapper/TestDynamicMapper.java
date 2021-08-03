package com.dy_name.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TestDynamicMapper {

    List<Map<String, Object>> query();

    void add();

    void create(@Param("name") String name);
}
