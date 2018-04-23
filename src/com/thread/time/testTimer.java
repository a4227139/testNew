package com.thread.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class testTimer {

	static volatile int timerIndex;
	public static void main(String[] args) {
		final Timer timer = new Timer();
		TimerTask task=new TimerTask() {
			@Override
			public void run() {
				if(timerIndex==1)
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timerIndex++;
				System.out.println(getTimes() + " index:" + timerIndex);
				if (timerIndex >= 5) {
					timer.cancel();
					System.out.println("停止");
				}
			}
		};
		timer.schedule(task, 0, 2000);//fixed-delay,下次执行时间根据当前时间调用schedule固定delay，
		//timer.scheduleAtFixedRate(task, 0, 2000);//fixed-rate，下次执行时间根据初始调用时间来算，并尽力将漏执行的任务补执行
	}
		
	private static String getTimes() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		Date date = new Date();
		date.setTime(System.currentTimeMillis());
		return format.format(date);
	}
}
