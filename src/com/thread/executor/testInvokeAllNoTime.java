package com.thread.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class testInvokeAllNoTime {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newFixedThreadPool(10);
		List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();
		Callable<Integer> task;
		for (int i = 0; i < 10; i++) {
			task = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					int num = new Random().nextInt(1000);
					if(num>800) throw new Exception("sleep too much");
					Thread.sleep(num);
					System.out.println(Thread.currentThread().getName()+ " sleep " + num);
					return num;
				}
			};
			tasks.add(task);
		}
		long s = System.currentTimeMillis();
		List<Future<Integer>> results = exec.invokeAll(tasks);
		System.out.println("invoke all task consumed£º" + (System.currentTimeMillis() - s)+ "ms");

		for (int i = 0; i < results.size(); i++) {
			try {
				System.out.println(results.get(i).get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		exec.shutdown();
	}
}
