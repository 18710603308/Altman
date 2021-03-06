package com.mzy.mapper.one;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author Jack Miao
 * @date 2021/1/11 20:43
 * @desc
 */
@Mapper
public interface OneMapper {

    List<Map<String, Object>> query();
}
