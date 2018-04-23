package com.algorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CoinChangeLimit {
	/**
	 * @param coins ����ÿһ��Ӳ�ҵı�ֵ������
	 * @param money ��Ҫ�������ֵ
	 */
	public static void changeCoins(int[] coins, int money) {

		Map<Integer, HashMap<Integer, Integer>> coinChangeMap = new HashMap<Integer, HashMap<Integer, Integer>>();
		Map<Integer, Integer> ownedMap = new HashMap<Integer, Integer>(); // ӵ�е�Ӳ�����༰����
		int valueKinds = coins.length;
		//��ӵ�еı�ֵ����ת����Map<��ֵ,�ñ�ֵ������>
		for (int kind = 0; kind < valueKinds; kind++) {
			if (ownedMap.containsKey(coins[kind])) {
				ownedMap.put(coins[kind], ownedMap.get(coins[kind]) + 1);
			} else {
				ownedMap.put(coins[kind], 1);
			}
		}
		for (int cents = 1; cents <= money; cents++) {
			// ������С��ֵ��Ӳ������ʱ������Ӳ���������
			int minCoins = cents;
			HashMap<Integer, Integer> minCoinMap = new HashMap<Integer, Integer>();
			coinChangeMap.put(cents, minCoinMap);
			// ����ÿһ����ֵ��Ӳ�ң����Ƿ����Ϊ���������֮һ
			for (int kind = 0; kind < valueKinds; kind++) {
				// ����ǰ��ֵ��Ӳ��С�ڵ�ǰ��cents��ֽ����Ⲣ���
				int coinVal = coins[kind];
				int oppCoinVal = cents - coinVal;
				if (oppCoinVal >= 0) {
					int tmpCount = getCoinCount(coinChangeMap.get(oppCoinVal)) + 1;
					if (tmpCount <= minCoins) { // Ҫ�õȺ�
						HashMap<Integer, Integer> subMap = coinChangeMap.get(oppCoinVal);// ����������Ž�
						HashMap<Integer, Integer> tmpMap = new HashMap<Integer, Integer>();
						if (subMap != null) {// Ҫcopyһ������
							tmpMap.putAll(subMap);
						}
						if (tmpMap.containsKey(coinVal)) {// ����Ѿ�������ǰ��ֵ�����һ
							tmpMap.put(coinVal, subMap.get(coinVal) + 1);
						} else {
							tmpMap.put(coinVal, 1);
						}
						// ȷ��ӵ�е��������ڵ��ڽ�������Ҽ�ֵû����ʧ
						if (isMapCoverSubMap(ownedMap, tmpMap) && getCoinsValue(tmpMap) == cents) {
							minCoinMap = tmpMap;
							minCoins = tmpCount;
						}
					}
				}
			}
			// ������СӲ����
			coinChangeMap.put(cents, minCoinMap);
			System.err.println("��ֵΪ " + (cents) + " ����СӲ���� : " + getCoinCount(coinChangeMap.get(cents)) + ",����Ϊ"
					+ coinChangeMap.get(cents));
		}
	}

	/**
	 * �ж�mapA�Ƿ���ȫ����mapB ��aΪ1=3,2=4 bΪ1=0,2=1��a��ȫ����b ��aΪ1=3,2=4 bΪ2=1,3=4��aû�и���b
	 */
	public static boolean isMapCoverSubMap(Map<Integer, Integer> A, Map<Integer, Integer> B) {
		if (A == null)
			return false;
		if (B == null || B.size() <= 0)
			return true;

		Map.Entry<Integer, Integer> aEntry = null;
		Iterator<Map.Entry<Integer, Integer>> iter = B.entrySet().iterator();
		while (iter.hasNext()) {
			aEntry = iter.next();
			int keyB = aEntry.getKey();
			if (!A.containsKey(keyB))
				return false;
			if (A.get(keyB) < aEntry.getValue())
				return false;
		}
		return true;
	}

	/**
	 * ���ص�ǰӲ����ϵ��ܼ�ֵ
	 */
	public static int getCoinsValue(Map<Integer, Integer> coinMap) {
		if (coinMap == null) return 0;
		int sum = 0;
		Map.Entry<Integer, Integer> aEntry = null;
		Iterator<Map.Entry<Integer, Integer>> iter = coinMap.entrySet().iterator();
		while (iter.hasNext()) {
			aEntry = iter.next();
			sum += aEntry.getKey() * aEntry.getValue();
		}
		return sum;
	}

	/**
	 * ���ص�ǰӲ����ϵ�Ӳ��������
	 */
	public static int getCoinCount(Map<Integer, Integer> B) {
		if (B == null) return 0;
		int sum = 0;
		Map.Entry<Integer, Integer> aEntry = null;
		Iterator<Map.Entry<Integer, Integer>> iter = B.entrySet().iterator();
		while (iter.hasNext()) {
			aEntry = iter.next();
			sum += aEntry.getValue();
		}
		return sum;
	}

	public static void main(String[] args) {
		int[] coinValue = new int[] { 50, 20, 9, 5, 2, 1, 1 };
		int money = 27;
		changeCoins(coinValue, money);
	}
}
