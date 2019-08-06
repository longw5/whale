package org.whale.math.cn;

import java.util.HashMap;
import java.util.Map;

class Solution {

	public static void main(String[] args) {
		
		int[] nums = {2, 7, 11, 15};
		int target = 9;
		int[] twoSum = twoSum(nums, target);
		
	}
	
	/**
	 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
	 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
	 * Example:
	 * Given nums = [2, 7, 11, 15], target = 9,
	 * Because nums[0] + nums[1] = 2 + 7 = 9,
	 * return [0, 1].
	 * @param nums
	 * @param target
	 * @return
	 */
	public static int[] twoSum(int[] nums, int target) {
		Map<Integer, Integer> lookup = new HashMap<>();
		int[] res = new int[2];
		for (int i = 0; i < nums.length; i++) {
			if (lookup.containsKey(target - nums[i])) {
				res = new int[] { lookup.get(target - nums[i]), i };
				break;
			} else {
				lookup.put(nums[i], i);
			}
		}
		
		for (int i : res) {
			System.out.println(i+" : "+nums[i]);
		}
		return res;
	}
}