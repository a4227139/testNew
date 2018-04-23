package com.thread;

public class testThreadLocal {

	ThreadLocal<Long> longLocal = new ThreadLocal<Long>() {
		protected Long initialValue() {
			return Thread.currentThread().getId();
		}
	};
	
	ThreadLocal<String> stringLocal = new ThreadLocal<String>();

	public void set() {
		//longLocal.set(Thread.currentThread().getId());//如果没有重写initialValue方法，则该句不能注释，否则空指针
		stringLocal.set(Thread.currentThread().getName());
	}

	public long getLong() {
		return longLocal.get();
	}

	public String getString() {
		return stringLocal.get();
	}

	public static void main(String[] args) throws InterruptedException {
		final testThreadLocal test = new testThreadLocal();
		test.set();
		System.out.println(test.getLong());
		System.out.println(test.getString());
		Thread thread1 = new Thread() {
			public void run() {
				test.set();
				System.out.println(test.getLong());
				System.out.println(test.getString());
			};
		};
		thread1.start();
		thread1.join();
		System.out.println(test.getLong());
		System.out.println(test.getString());
	}
}
