package com.thread.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class testScheduledExecutorService {
	
	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
	static volatile int index;
	public static void main(String[] args) throws Exception{
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
		MyTimeTask task1 = new MyTimeTask("Thread1", 1);
		MyTimeTask task2 = new MyTimeTask("Thread2", 2);
		
		//pool.scheduleAtFixedRate(task1, 0, 1, TimeUnit.SECONDS);
		pool.scheduleWithFixedDelay(task2, 0, 1, TimeUnit.SECONDS);
		//pool.schedule(task1, 0, TimeUnit.SECONDS);//只接受延迟任务，不接受定时任务
		Thread.sleep(1000*10);
		pool.shutdown();
	}

}

class MyTimeTask implements Runnable {

	String name;
	int value;

	public MyTimeTask(String name, int value) {
		super();
		this.name= name;
		this.value = value;
	}

	@Override
	public void run() {
		try {
			if(testScheduledExecutorService.index==1){
				Thread.sleep(3 * 1000);
			}
			testScheduledExecutorService.index++;
			System.out.println(testScheduledExecutorService.format.format(new Date()) + " " + name + ":" + testScheduledExecutorService.index);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
