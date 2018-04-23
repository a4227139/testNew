package com.collection;

import java.util.Comparator;
import java.util.TreeMap;

public class testTreeMap {

	public static void main(String[] args) {
		TreeMap<Integer, Integer> map1 = new TreeMap<Integer, Integer>(); // Ä¬ÈÏµÄTreeMapÉıĞòÅÅÁĞ
		TreeMap<Integer, Integer> map2 = new TreeMap<Integer, Integer>(
			new Comparator<Integer>() {
				public int compare(Integer a, Integer b) {
					return b - a;
				}
			});
		map1.put(1, 2);
		map1.put(2, 4);
		map1.put(7, 1);
		map1.put(5, 2);
		System.out.println("map1=" + map1);
		
		map2.put(1, 2);
		map2.put(2, 4);
		map2.put(7, 1);
		map2.put(5, 2);
		System.out.println("Map2=" + map2);

		
	}
}
