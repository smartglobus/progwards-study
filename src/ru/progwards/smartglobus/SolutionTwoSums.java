package ru.progwards.smartglobus;

class SolutionTwoSums {
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        for(int i = 0; i < nums.length; i++){
            for(int j = i+1; j < nums.length; j++){
                if(nums[i]+nums[j]==target){
                    result[0] = i;
                    result[1] = j;
                    break;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] a = new int[1000000];
        int sum = 0;
        for (int i = 0; i < 1000000; i++){
            a[i] = (int)(Math.random()*(2000000) - 1000000);
            sum += a[i];
        }
        System.out.println(sum/1000000);
    }
}
