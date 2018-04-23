package com.thread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class testFuture {

	public static class MyCallable implements Callable<String> {
		private int flag = 0;

		public MyCallable(int flag) {
			this.flag = flag;
		}

		public String call() throws Exception {
			if (this.flag == 0) {
				return "flag = 0";
			}
			if (this.flag == 1) {
				try {
					while (true) {//��ѭ��
						System.out.println("looping...");
						Thread.sleep(2000);
					}
				} catch (InterruptedException e) {
					System.out.println("Interrupted");
				}
				return "false";
			} else {
				throw new Exception("Bad flag value!");
			}
		}
	}

	public static void main(String[] args) {
		// ����3��Callable���͵�����
		MyCallable task1 = new MyCallable(0);
		MyCallable task2 = new MyCallable(1);
		MyCallable task3 = new MyCallable(2);
		// ����һ��ִ������ķ���
		ExecutorService es = Executors.newFixedThreadPool(3);
		try {
			// �ύ��ִ��������������ʱ������һ��Future����
			// �����õ�����ִ�еĽ���������쳣�ɶ����Future������в���
			Future<String> future1 = es.submit(task1);
			// ��õ�һ������Ľ�����������get��������ǰ�̻߳�ȴ�����ִ����Ϻ������ִ��
			System.out.println("task1: " + future1.get());
			Future<String> future2 = es.submit(task2);
			// �ȴ�5�����ֹͣ�ڶ���������Ϊ�ڶ���������е�������ѭ��
			Thread.sleep(5000);
			System.out.println("task2 cancel: " + future2.cancel(true));//true��ʾʹ���ж�
			//System.out.println("task2: " + future2.get());//�׳�CancellationException������ʱ�쳣����������future3����䱻����
			// ��ȡ������������������Ϊִ�е���������������쳣
			// �����������佫�����쳣���׳�
			Future<String> future3 = es.submit(task3);
			System.out.println("task3: " + future3.get());
		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			System.out.println("ExecutionException");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ֹͣ����ִ�з���
		es.shutdownNow();
	}
}
