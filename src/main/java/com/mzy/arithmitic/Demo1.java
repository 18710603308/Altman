package com.mzy.arithmitic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author Jack Miao
 * @date 2021/4/20 13:47
 * @desc
 */
public class Demo1 {
    /**
     * 一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 。请你找出所有满足条件且不重复的三个元素组和。
     *
     * 注意：答案中不可以包含重复的三个元素组和。
     *
     * 示例：
     *
     * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
     *
     * 满足要求的三个元素组和的集合为：
     * [
     *   [-1, 0, 1],
     *   [-1, -1, 2]
     * ]
     */
    public static void main(String[] args) {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
        //int[] nums = {-1, 0, 1, 2, -1, -4, 0, 0, 4};
        //保存结果集合
        List<Integer[]> resList = new ArrayList<>();
        //保存不重复的值并判重
        HashSet noRepeatSet = new HashSet();
        //是否出现过都为0
        boolean isAllO = false;
        int[] curArr;
        int k = 0;

        for (int i = 0; i < nums.length; i++) {
            int a = 0 - nums[i];
            //保存判断结果值b、c的值
            Map<Integer, Integer> map = new HashMap<>();
            for (int j = i + 1; j < nums.length; j++) {
                k++;
                if (map.containsKey(a - nums[j])) {
                    //已添加过跳过本轮
                    if (a == 0 && nums[i] == 0 && nums[j] == 0) {
                        if (isAllO) {
                            continue;
                        } else {
                            //全为 0 添加到集合
                            resList.add(new Integer[]{map.get(a - nums[j]), nums[j], nums[i]});
                            isAllO = true;
                            continue;
                        }
                    }
                    curArr = new int[]{map.get(a - nums[j]), nums[j], nums[i]};
                    //排序
                    Arrays.sort(curArr);
                    //判重
                    if (!noRepeatSet.contains(curArr[0] + "" + curArr[1] + "" + curArr[2])) {
                        resList.add(new Integer[]{map.get(a - nums[j]), nums[j], nums[i]});
                        //保存此次数组值
                        noRepeatSet.add(curArr[0] + "" + curArr[1] + "" + curArr[2]);
                    }
                } else {
                    map.put(nums[j], nums[j]);
                }
            }
        }

        for (Integer[] integers : resList) {
            System.out.println(Arrays.toString(integers));
        }
    }
}
