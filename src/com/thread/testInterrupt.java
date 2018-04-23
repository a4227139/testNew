package com.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class testInterrupt {

	public static void main(String args[]) throws Exception {
		testNotBlockingThread();
		testBlockingThread();
		testBlockingThread2();
		testReadBlockingThread();
	}
	
	public static void testNotBlockingThread() throws InterruptedException{
		NotBlockingThread notBlockingThread = new NotBlockingThread();
		System.out.println("NotBlockingThread Starting...");
		notBlockingThread.start();
		Thread.sleep(3000);
		System.out.println("Interrupting NotBlockingThread...");
		notBlockingThread.interrupt();// ��Ϊ�߳�������״̬�ģ�û��������interruptֻ����λ���ѣ��������ж��߳�
		Thread.sleep(3000);
		System.out.println("Asking NotBlockingThread to stop...");
		notBlockingThread.stop = true; // ʹ�ù������ʱ��õĽ����̵߳ķ�������������������߳�������״̬�ģ����޷���鹲������Ӷ��޷�����
		Thread.sleep(3000);
		System.out.println("Main thread stop...");
		// System.exit(0); //stop application
	}
	
	public static void testBlockingThread() throws InterruptedException{
		BlockingThread BlockingThread = new BlockingThread();
		System.out.println("BlockingThread Starting...");
		BlockingThread.start();
		Thread.sleep(3000);
		System.out.println("Interrupting BlockingThread...");
		BlockingThread.interrupt();//��Ϊʹ���˹�������ķ�ʽ�����Թ�interrupt�ǲ��еģ��̲߳������˳�
		Thread.sleep(3000);
		System.out.println("Asking BlockingThread to stop...");
		BlockingThread.stop = true; 
		BlockingThread.interrupt();//�������ʹ�ò������߳�Ѹ���˳�
		Thread.sleep(3000);
		System.out.println("Main thread stop...");
		// System.exit(0); //stop application
	}
	
	public static void testBlockingThread2() throws InterruptedException{
		BlockingThread2 BlockingThread2 = new BlockingThread2();
		BlockingThread2.start();
		BlockingThread2.interrupt();//�����߳�һ��ʼ��������״̬����ֻҪ��λ�ˣ��߳̽���sleep��wait��join�����������״̬Ҳ�������쳣�˳�
	}
	
	public static void testReadBlockingThread() throws InterruptedException{
		ReadBlockingThread readBlockingThread = new ReadBlockingThread();
		readBlockingThread.start();
		readBlockingThread.interrupt();//��ͨ����io���������жϣ����Ը��������interrupt������NIO�����channel����ClosedByInterruptException���ж�
	}
}

class NotBlockingThread extends Thread{
	public volatile boolean stop = false;
	public void run(){
		while (!stop) {
			System.out.println("Thread is running...");
			long time = System.currentTimeMillis();
			while ((System.currentTimeMillis() - time < 1000)) {// ������
			}
		}
		System.out.println("Thread exiting under request...");
	}
}

class ReadBlockingThread extends Thread{
	public void run(){
		try {
			FileInputStream inputStream=new FileInputStream(new File("I:\\debug2.log"));
			FileChannel channel=inputStream.getChannel();
			/*byte[] b=new byte[100];
			while (inputStream.read(b)>0) {				
			}*/
			ByteBuffer buffer=ByteBuffer.allocate(100);
			while(channel.read(buffer)>0){
			}
			System.out.println("read done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


class BlockingThread extends Thread{
	public volatile boolean stop = false;
	public void run(){
		while (!stop) {
			System.out.println("Thread is running...");
			try {
				Thread.sleep(1000);//����
			} catch (InterruptedException e) {
				System.out.println("Thread interrupted...");
				//Do some finishing work
			}
		}
		System.out.println("Thread exiting under request...");
	}
}

class BlockingThread2 extends Thread{
	public void run(){
		System.out.println("Thread is running...");
		long time = System.currentTimeMillis();
		while ((System.currentTimeMillis() - time < 5000)) {// �������ؿ�ת5��
		}
		try {
			Thread.sleep(5000);//����
		} catch (InterruptedException e) {
			System.out.println("Thread interrupted...");
		}
	}
}