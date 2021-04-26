package com.mzy.concurrent.concurrent_skip;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Jack Miao
 * @date 2021/4/20 15:01
 * @desc
 */
public class ConcurrentSkipListMapDemo {

    public static void main(String[] args) {
        /**
        *  ConcurrentSkipListMap提供了一种线程安全的并发访问的排序映射表。
        *  内部是SkipList（跳表）结构实现，在理论上能够O(log(n))时间内完成查找、插入、删除操作
        *  ConcurrentSkipListMap存储结构跳跃表（SkipList）：
        *   1、最底层的数据节点按照关键字升序排列。
        *   2、包含多级索引，每个级别的索引节点按照其关联数据节点的关键字升序排列。
        *   3、高级别索引是其低级别索引的子集。
        *   4、如果关键字key在级别level=i的索引中出现，则级别level<=i的所有索引中都包含key。
        */
        /*ConcurrentSkipListMap listMap = new ConcurrentSkipListMap();
        listMap.put("1", 3);
        listMap.put("2", 5);
        System.out.println(listMap);
        System.out.println(listMap.computeIfPresent("2", (a, b)->{
            System.out.println(a + ":" + b);
            return null;
        }));
        System.out.println(listMap);

        listMap.replaceAll((a, b)->{
            System.out.println(a + "===" + b);
            return "";
        });
        System.out.println();
        System.out.println(listMap);*/


    }
}
