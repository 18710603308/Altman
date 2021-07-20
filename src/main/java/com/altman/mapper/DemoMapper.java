package com.altman.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author mzy
 * @date 2021/7/20 23:23
 */
@Mapper
@Repository
public interface DemoMapper {

    List<Map<String, Object>> demo();
}
