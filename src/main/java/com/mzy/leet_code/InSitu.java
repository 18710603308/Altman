package com.mzy.leet_code;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * @author mzy
 * @date 2021/7/10 17:11
 */
public class InSitu {

    public static void rotate(int[] nums, int k) {
        int[] news = new int[nums.length];
        int index = 0;
        if(k > nums.length){
            for (int i = nums.length-1; i >= 0 ; i--) {
                news[index] = nums[i];
                ++index;
            }
        }else{
            // k-length 为新数组的head
            for (int i = nums.length - k; i < nums.length; i++) {
                news[index] = nums[i];
                ++index;
            }

            for (int i = 0; i < nums.length - k; i++) {
                news[index] = nums[i];
                ++index;
            }
        }
        for (int i = 0; i < news.length; i++) {
            nums[i] = news[i];
        }
    }

    /**
     * 思路:
     *  nums循环 若有0则往后移交换,一个标识记录0的个数,若是零则不用移动,非零向前移动0的个数个位置
     * @param nums
     */
    public static void moveZeroes(int[] nums) {
        // 记录0的个数
        int num = 0;
        for (int i = 0; i < nums.length; i++) {
            // 首位是0不做移动操作
            if(nums[i] == 0 && i == 0){
                num += 1;
                continue;
            }
            // 判断0的个数是否大于剩余未操作的下标
            if((nums.length - i + num) > num){
                if(nums[i] != 0){
                    nums[i - num] = nums[i];
                }else{
                    num++;
                }
                // 超过直接补零
            }
        }
        for (int i1 = nums.length - num; i1 < nums.length; i1++) {
            nums[i1] = 0;
        }
    }

    public static int[] twoSum(int[] numbers, int target) {
        // 二分查找
        for (int i = 0; i < numbers.length; i++) {
            int left = i + 1;
            int right = numbers.length - 1;
            // 计算target值
            int two = target - numbers[i];
            while(left <= right){
                int mid = left + (right - left) / 2;
                if(mid != i && two == numbers[mid]){
                    return new int[]{i + 1, mid + 1};
                }else if(two < numbers[mid]){
                    right = mid - 1;
                }else{
                    left = mid + 1;
                }
            }
        }
        return new int[]{-1, -1};
    }

    public String reverseWords(String s) {
        String[] strArr = s.split(" ");
        String str = "";
        for(int i = 0; i < strArr.length; i++){
            final char[] chars = strArr[i].toCharArray();
            for (int i1 = chars.length - 1; i1 >= 0 ; i1--) {
                str += chars[i1];
            }
            if(i != strArr.length - 1){
                str += " ";
            }
        }
        return str;
    }


    public static int lengthOfLongestSubstring(String s) {
        // 滑动窗口
        final HashSet<Integer> integers = new HashSet<>();
        final HashSet<Character> characters = new HashSet<>();
        final char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int right = i + 1;
            characters.add(chars[i]);
            while (right < chars.length && !characters.contains(chars[right])){
                characters.add(chars[right]);
                right++;
            }
            integers.add(characters.size());
            characters.clear();
        }
        return integers.stream().max(Integer::compareTo).get();
    }


    public static boolean checkInclusion(String s1, String s2) {
        int[] one = new int[26];
        int[] two = new int[26];

        final char[] chars = s1.toCharArray();
        final char[] chars1 = s2.toCharArray();
        // 计算s1中字符出现次数
        for (int i = 0; i < chars.length; i++) {
            ++one[chars[i] - 'a'];
        }
        // 计算s2区间
        for (int i = 0; i < chars.length; i++) {
            ++two[chars1[i] - 'a'];
        }
        int n = chars.length;
        // 左指针
        int left = 0;
        int right = n;
        while(right < chars1.length){
            if(Arrays.equals(one, two)){
                return true;
            }
            --two[chars1[left] - 'a'];
            ++two[chars1[right] - 'a'];
            if(Arrays.equals(one, two)){
                return true;
            }
            ++left;
            ++right;
        }
        return false;
    }

    public static void main(String[] args) {
        String s1 = "adc";
        String s2 = "dcda";
        System.out.println(checkInclusion(s1, s2));
    }
}
