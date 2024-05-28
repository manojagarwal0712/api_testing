package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        int arr[] = {9,9,9};
        int ans [] = addOneToArray(arr);
        for (int a:
             ans) {
            System.out.println(a);
        }
    }

    public static int[] addOneToArray(int arr[]){
        int carryover = 1;
        int sum = 0;
        for (int i = arr.length-1; i>=0; i--){
            sum = arr[i]+carryover;
            carryover = sum%10;
            sum = sum/10;
            arr[i] = sum;
        }
        if (carryover>=0){
            int [] ans = new int[arr.length+1];
            ans[0] = carryover;
            return ans;
        }else {
            return arr;
        }
    }

}