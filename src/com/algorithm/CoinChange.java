package com.algorithm;

import java.util.HashMap;  
import java.util.Map; 
public class CoinChange {    
    /**   
     * @param coins  ����ÿһ��Ӳ�ҵı�ֵ������   
     * @param money  ��Ҫ�������ֵ   
     */   
    public static void changeCoins(int[] coins,int money) {    
        int[] coinsUsed = new int[money + 1];    // ������ֵΪi��ֽ�������������СӲ����    
        int valueKinds = coins.length;      //Ӳ����������  
        coinsUsed[0] = 0;  //0Ԫ�����Ž�  
        Map<Integer,HashMap<Integer,Integer>> coinChangeMap = new HashMap<Integer,HashMap<Integer,Integer>>();  
  
        for (int cents = 1; cents <= money; cents++) {    
            // ������С��ֵ��Ӳ������ʱ������Ӳ���������    
            int minCoins = cents;    
            HashMap<Integer,Integer> minCoinMap = new HashMap<Integer,Integer>();//���������ֵ�ľ������㷽��  
            minCoinMap.put(1, cents);  //��ʼ��������cents��1ԪӲ��
            coinChangeMap.put(cents, minCoinMap);  
            // ����ÿһ����ֵ��Ӳ�ң����Ƿ����Ϊ���������֮һ    
            for (int kind = 0; kind < valueKinds; kind++) {                 
                int coinVal = coins[kind];  
                int oppCoinVal = cents - coinVal;  //�������ܶ�͵�ǰӲ����ֵ�Ĳ�
                if (oppCoinVal>=0) {  // ����ǰӲ����ֵС�ڵ�ǰ�������ܶ���ֽ����Ⲣ���    
                    int tmpCount = coinsUsed[oppCoinVal] + 1;  //coinsUsed[oppCoinVal]��ʾ֮ǰ������������ܶ�oppCoinVal������СӲ������+1��ʾ����ӵ�ǰӲ�� 
                    if (tmpCount < minCoins) {    
                        HashMap<Integer,Integer> subMap = coinChangeMap.get(oppCoinVal);//����������Ž�  
                        HashMap<Integer,Integer> tmpMap = new HashMap<Integer,Integer>();  
                        if(subMap != null){//Ҫcopyһ������  
                            tmpMap.putAll(subMap);  
                        }  
                        if(tmpMap.containsKey(coinVal)){//����Ѿ�������ǰ��ֵ�����һ  
                            tmpMap.put(coinVal, subMap.get(coinVal)+1);  
                        }else{  
                            tmpMap.put(coinVal, 1);  
                        }  
                        minCoins = tmpCount;    
                        minCoinMap = tmpMap;  
                    }  
                }    
            }    
            // ������СӲ����    
            coinsUsed[cents] = minCoins;   
            coinChangeMap.put(cents, minCoinMap);  
  
            System.err.println("��ֵΪ " + (cents) + " ����СӲ���� : "   
                    + coinsUsed[cents]+",����Ϊ"+ coinChangeMap.get(cents));    
        }    
    }    
  
    public static void main(String[] args) {    
        // Ӳ����ֵԤ���Ѿ�����������    
        //int[] coinValue = new int[] { 50, 20, 10, 5, 2,1 };   
        int[] coinValue = new int[] { 50, 20, 10, 6, 1 };   
        // ��Ҫ�������ֵ    
        int money = 18;    
        // ����ÿһ����ֵ�����������СӲ������0�ŵ�Ԫ�������ã�����Ҫ���1    
        changeCoins(coinValue,  money);    
    }    
  
}  
