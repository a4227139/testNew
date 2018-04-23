package com.thread.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class testInvokeAny {

	public static void main(String[] args) {
		ExecutorService exec=Executors.newFixedThreadPool(3);
		List<Callable<Integer>> tasks=new ArrayList<>();
		Callable<Integer> task;
		for(int i=0;i<3;i++){
			task=new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					long start=System.currentTimeMillis();
					Random random=new Random();
					int sleepTime=random.nextInt(1000);
					if(sleepTime<500) throw new Exception("too fast");
					Thread.sleep(sleepTime);
					System.out.println(Thread.currentThread()+" consume:"+(System.currentTimeMillis()-start)+"ms");
					return 7800*14;
				}
			};
			tasks.add(task);
		}
		
		try {
			long start=System.currentTimeMillis();
			Integer i=exec.invokeAny(tasks);
			System.out.println("consume:"+(System.currentTimeMillis()-start)+"ms result:"+i);
		} catch (InterruptedException | ExecutionException e) {
			exec.shutdownNow();//如果全部抛异常，则需要强制关闭，否则线程池永远不退出
			e.printStackTrace();
		}
		exec.shutdown();//需要关闭，否则线程池永远不退出
	}
}
