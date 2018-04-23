package com.thread.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class testSemaphore {

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		// ֻ��5���߳�ͬʱ����
		final Semaphore semaphore = new Semaphore(5);
		// ģ��20���ͻ��˷���
		for (int index = 0; index < 20; index++) {
			final int NO = index;
			Runnable run = new Runnable() {
				public void run() {
					try {
						// ��ȡ���
						semaphore.acquire();
						System.out.println("Accessing: " + NO);
						Thread.sleep((long) (Math.random() * 10000));
						// ��������ͷ�
						semaphore.release();
						System.out.println("Exit: " + NO);
						System.out.println("-----------------" + semaphore.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			exec.execute(run);
		}
		// �˳��̳߳�
		exec.shutdown();
	}
}
