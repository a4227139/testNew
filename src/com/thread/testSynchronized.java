package com.thread;
/**
 * 
 *synchronized 关键字锁住三种不同的对象，class、实例或括号后面的对象，以下三种可以并发执行，因为锁住的对象不一样。
 */
public class testSynchronized {
	
	public static void main(String[] args) {
		final Data data=new Data();
		new Thread(new Runnable() {
			@Override
			public void run() {
				data.objectAdd();
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				data.instanceAdd();
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				data.staticAdd();
			}
		}).start();
		
	}

}
class Data{
	Integer i=new Integer(0);
	
	public void objectAdd(){
		System.out.println("objectAdd start");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("objectAdd end");
	}
	
	public synchronized void instanceAdd(){
		System.out.println("instanceAdd start");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("instanceAdd end");
	}
	
	public synchronized static void staticAdd() {
		System.out.println("staticAdd start");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("staticAdd end");
	}
	
	
}