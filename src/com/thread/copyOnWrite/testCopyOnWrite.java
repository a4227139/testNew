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

		//因为该线程第三个启动，而cowlist已经添加完成，所以获得到更新后的cowlist，如果打开第二个线程中的Thread.sleep则得到旧的cowlist
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
