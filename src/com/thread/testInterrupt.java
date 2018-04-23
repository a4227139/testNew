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
		notBlockingThread.interrupt();// 因为线程是运行状态的，没有阻塞，interrupt只是置位而已，并不能中断线程
		Thread.sleep(3000);
		System.out.println("Asking NotBlockingThread to stop...");
		notBlockingThread.stop = true; // 使用共享变量时最好的结束线程的方法，但问题在于如果线程是阻塞状态的，则无法检查共享变量从而无法结束
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
		BlockingThread.interrupt();//因为使用了共享变量的方式，所以光interrupt是不行的，线程并不会退出
		Thread.sleep(3000);
		System.out.println("Asking BlockingThread to stop...");
		BlockingThread.stop = true; 
		BlockingThread.interrupt();//两个结合使用才能让线程迅速退出
		Thread.sleep(3000);
		System.out.println("Main thread stop...");
		// System.exit(0); //stop application
	}
	
	public static void testBlockingThread2() throws InterruptedException{
		BlockingThread2 BlockingThread2 = new BlockingThread2();
		BlockingThread2.start();
		BlockingThread2.interrupt();//哪怕线程一开始不在阻塞状态，但只要置位了，线程进入sleep、wait、join等引起的阻塞状态也立即抛异常退出
	}
	
	public static void testReadBlockingThread() throws InterruptedException{
		ReadBlockingThread readBlockingThread = new ReadBlockingThread();
		readBlockingThread.start();
		readBlockingThread.interrupt();//普通的流io操作不可中断，所以根本不理会interrupt，但是NIO里面的channel会抛ClosedByInterruptException而中断
	}
}

class NotBlockingThread extends Thread{
	public volatile boolean stop = false;
	public void run(){
		while (!stop) {
			System.out.println("Thread is running...");
			long time = System.currentTimeMillis();
			while ((System.currentTimeMillis() - time < 1000)) {// 不阻塞
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
				Thread.sleep(1000);//阻塞
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
		while ((System.currentTimeMillis() - time < 5000)) {// 不阻塞地空转5秒
		}
		try {
			Thread.sleep(5000);//阻塞
		} catch (InterruptedException e) {
			System.out.println("Thread interrupted...");
		}
	}
}