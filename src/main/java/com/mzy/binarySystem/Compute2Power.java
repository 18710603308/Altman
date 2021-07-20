package com.mzy.binarySystem;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mzy
 * @date 2021/5/31 23:09
 */
public class Compute2Power {

    public static void main(String[] args) {
        int i = 3;
        i >>= 1;
        //System.out.println(8 & 7);
        /*1
        100
        10000
        1000000
*/
        //System.out.println(0xaaaaaaaa);
        int[] arrs = new int[]{22,4,6,4,7};
        System.out.println(checkSubarraySum(arrs, 4));
        final HashMap<Object, Object> hashMap = new HashMap<>();
    }

    // 替代Integer.toBinaryString()
    public static String toBinaryString(int num) {
        if (num == 0) return ""+0;
        String result = "";
        // 左面0的个数
        int n = Integer.numberOfLeadingZeros(num);
        num <<= n;
        for (int i=0; i<32-n; ++i) {
            int x = (Integer.numberOfLeadingZeros(num) == 0)?1:0;
            result += x;
            num <<= 1;
        }
        return result;
    }

    public static boolean checkSubarraySum(int[] nums, int k) {
        int flag = 0;
        Map <Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, -1);
        for(int i = 0;i < nums.length; ++i){
            flag = (flag + nums[i]) % k;
            if(map.containsKey(flag)){
                if((i-map.get(flag)) >= 2){
                    return true;
                }
            }else{
                map.put(flag, i);
            }
        }
        return false;
    }
}
