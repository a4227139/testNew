package com.thread.copyOnWrite;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class testCopyOnWrite {

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<>();
		list.add(1);list.add(2);list.add(3);list.add(4);list.add(5);
		final CopyOnWriteArrayList<Integer> cowlist = new CopyOnWriteArrayList<>(list);
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (Integer i : cowlist) {
					try {
						Thread.sleep(100);
						System.out.println(System.currentTimeMillis()+":"+Thread.currentThread().getName()+":"+i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(System.currentTimeMillis()+": add");
				//Thread.sleep(100);
				cowlist.add(6);
			}
		}).start();

		//��Ϊ���̵߳�������������cowlist�Ѿ������ɣ����Ի�õ����º��cowlist������򿪵ڶ����߳��е�Thread.sleep��õ��ɵ�cowlist
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (Integer i : cowlist) {
					try {
						Thread.sleep(100);
						System.out.println(System.currentTimeMillis()+":"+Thread.currentThread().getName()+":"+i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
