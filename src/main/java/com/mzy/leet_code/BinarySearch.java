package com.mzy.leet_code;

/**
 * @author mzy
 * @date 2021/7/6 21:14
 */
public class BinarySearch {

    public int search(int[] nums, int target) {
        // 定义下标值
        int flag = 0;
        // 最小下标
        int left = 0;
        // 最大下标
        int right = nums.length - 1;
        while(left <= right){
            flag = left + (right - left)/2;
            // 是否是目标值
            if(nums[flag] == target){
                return flag;
            }
            //
            if(target < nums[flag]){
                right = flag;
            }else{
                left = flag;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nu = new int[]{-4,-1,0,3,10};
        sortedSquares(nu);
    }


    public static int[] sortedSquares(int[] nums) {
        // 查找正负分界
        int n = nums.length;
        int flag = -1;
        for(int i = 0;i < n; i++){
            if(nums[i] < 0){
                flag = i;
            }else{
                break;
            }
        }

        // 定义指针
        // 负数的尾节点
        int i = flag;
        // 正数的头点
        int j = flag + 1;

        int l = 0;

        // 定义新数组
        int[] news = new int[n + 1];
        while(i >= 0 || j < n){
            // 都是正数
            if(i == -1){
                news[l] = nums[j] * nums[j];
                ++j;
                // 都是负数
            }else if(j == n){
                news[l] = nums[i] * nums[i];
                --i;
            }else if(nums[i] * nums[i] < nums[j] * nums[j]){
                news[l] = nums[i] * nums[i];
                --i;
            }else{
                news[l] = nums[j] * nums[j];
                ++j;
            }
            ++l;
        }
        return nums;
    }
}
