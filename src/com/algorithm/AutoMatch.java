package com.algorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AutoMatch {
	
	//static float[] barcodeQty=new float[]{0.5f,1,1,1.5f,2,2,2,3,4};
	static float[] barcodeQty=new float[]{1,2,3,4,5};
	public static void main(String[] args) {
		float needQty=10;
		System.out.println(autoRecursion(needQty,barcodeQty.length-1,false));
	}
	
	
	/**   
     * @param coins  保存每一种硬币的币值的数组   
     * @param money  需要找零的面值   
     */   
    public static void changeCoins(int[] coins,  int money) {    
  
        Map<Integer,HashMap<Integer,Integer>> coinChangeMap = new HashMap<Integer,HashMap<Integer,Integer>>();  
        Map<Integer,Integer> ownedMap = new HashMap<Integer,Integer>(); //拥有的硬币种类及数量  
        int valueKinds = coins.length;  
        for (int kind = 0; kind < valueKinds; kind++) {          
            ownedMap.put(coins[kind],2);  
        }  
        for (int cents = 1; cents <= money; cents++) {    
            // 当用最小币值的硬币找零时，所需硬币数量最多    
            int minCoins = cents;    
            HashMap<Integer,Integer> minCoinMap = new HashMap<Integer,Integer>(); 
            coinChangeMap.put(cents, minCoinMap);  
            // 遍历每一种面值的硬币，看是否可作为找零的其中之一    
            for (int kind = 0; kind < valueKinds; kind++) {                 
                // 若当前面值的硬币小于当前的cents则分解问题并查表    
                int coinVal = coins[kind];  
                int oppCoinVal = cents - coinVal;  
                if (oppCoinVal>=0) {    
                    int tmpCount = getCoinCount(coinChangeMap.get(oppCoinVal))+1;    
                    if (tmpCount <= minCoins) {  //要用等号  
                        HashMap<Integer,Integer> subMap = coinChangeMap.get(oppCoinVal);//子问题的最优解  
                        HashMap<Integer,Integer> tmpMap = new HashMap<Integer,Integer>();  
                        if(subMap != null){//要copy一份数据  
                            tmpMap.putAll(subMap);  
                        }  
                        if(tmpMap.containsKey(coinVal)){//如果已经包含当前面值，则加一  
                            tmpMap.put(coinVal, subMap.get(coinVal)+1);  
                        }else{  
                            tmpMap.put(coinVal, 1);  
                        }  
                        //确保拥有的数量大于等于结果数量且价值没有损失  
                        if(isMapCoverSubMap(ownedMap, tmpMap)  
                                        &&  getCoinsValue(tmpMap) == cents){  
                            minCoinMap = tmpMap;
                            minCoins=tmpCount;
                        }  
                    }  
                }    
            }    
            // 保存最小硬币数    
            coinChangeMap.put(cents, minCoinMap);  
            System.err.println("面值为 " + (cents) + " 的最小硬币数 : "   
                    +getCoinCount(coinChangeMap.get(cents))+",货币为"+ coinChangeMap.get(cents));    
        }    
    }    
      
    /** 
     *  判断mapA是否完全覆盖mapB 
     *  如a为1=3,2=4  b为1=0,2=1，a完全覆盖b 
     *  如a为1=3,2=4  b为2=1,3=4，a没有覆盖b 
     */  
    public static boolean isMapCoverSubMap(Map<Integer,Integer> A,Map<Integer,Integer> B){  
        if(A == null) return false;  
        if(B == null || B.size() <= 0) return true;  
  
        Map.Entry<Integer, Integer> aEntry = null;  
        Iterator<Map.Entry<Integer, Integer>> iter = B.entrySet().iterator();  
        while(iter.hasNext()){  
            aEntry = iter.next();  
            int keyB = aEntry.getKey();  
            if(!A.containsKey(keyB))  return false;  
            if(A.get(keyB) < aEntry.getValue()) return false;  
        }  
        return true;  
    }  
  
    /** 
     *  返回当前硬币组合的总价值 
     */  
    public static int  getCoinsValue(Map<Integer,Integer> coinMap){  
        if(coinMap == null ) return 0;  
  
        int sum = 0;  
        Map.Entry<Integer, Integer> aEntry = null;  
        Iterator<Map.Entry<Integer, Integer>> iter = coinMap.entrySet().iterator();  
        while(iter.hasNext()){  
            aEntry = iter.next();  
            sum +=  aEntry.getKey() * aEntry.getValue();  
        }  
  
        return sum;  
    }  
  
    /** 
     *  返回当前硬币组合的硬币总数量 
     */  
    public static int getCoinCount(Map<Integer,Integer> B){  
        if(B == null ) return 0;  
  
        int sum = 0;  
        Map.Entry<Integer, Integer> aEntry = null;  
        Iterator<Map.Entry<Integer, Integer>> iter = B.entrySet().iterator();  
        while(iter.hasNext()){  
            aEntry = iter.next();  
            sum +=   aEntry.getValue();  
        }  
  
        return sum;  
    } 
	
	
	
	
	
	public static int autoRecursion(float needQty,int index,boolean selected){
		if(needQty==0) return 1;
		if(needQty<0) return 0;
		if(index<0) return 0;
		if(selected)
		System.out.println("needQty:"+needQty+" barcodeQty:"+barcodeQty[index]+" index:"+index);
		return autoRecursion(needQty,index-1,false)+autoRecursion(needQty-barcodeQty[index], index-1,true);
	}
}
