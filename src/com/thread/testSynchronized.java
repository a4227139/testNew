package com.thread;
/**
 * 
 *synchronized �ؼ�����ס���ֲ�ͬ�Ķ���class��ʵ�������ź���Ķ����������ֿ��Բ���ִ�У���Ϊ��ס�Ķ���һ����
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