package com.normal;

import java.util.BitSet;

public class testBitSet {

	public static void main(String[] args) {
		int[] array = new int[] { 1, 2, 3, 77, 0, 32 , 3 };
		BitSet bitSet = new BitSet(6);//��ΪBitSet�ײ���long[]ʵ�֣����Բ���д������Ͷ�Ҫ��һ��long����64λ
		// ������������bitmap
		for (int i = 0; i < array.length; i++) {
			bitSet.set(array[i]);//��ʼ��bitset�������긳ֵ
		}
		System.out.println(bitSet.size());//��Ϊ77����64��������Ҫ����long����128λ
		System.out.println(bitSet.get(32));
	}
}
