package com.normal;

import java.util.BitSet;

public class testBitSet {

	public static void main(String[] args) {
		int[] array = new int[] { 1, 2, 3, 77, 0, 32 , 3 };
		BitSet bitSet = new BitSet(6);//因为BitSet底层用long[]实现，所以不管写多少最低都要用一个long，即64位
		// 将数组内容组bitmap
		for (int i = 0; i < array.length; i++) {
			bitSet.set(array[i]);//初始化bitset，将坐标赋值
		}
		System.out.println(bitSet.size());//因为77大于64，所以需要两个long，即128位
		System.out.println(bitSet.get(32));
	}
}
