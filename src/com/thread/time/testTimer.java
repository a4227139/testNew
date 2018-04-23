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
					System.out.println("ֹͣ");
				}
			}
		};
		timer.schedule(task, 0, 2000);//fixed-delay,�´�ִ��ʱ����ݵ�ǰʱ�����schedule�̶�delay��
		//timer.scheduleAtFixedRate(task, 0, 2000);//fixed-rate���´�ִ��ʱ����ݳ�ʼ����ʱ�����㣬��������©ִ�е�����ִ��
	}
		
	private static String getTimes() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		Date date = new Date();
		date.setTime(System.currentTimeMillis());
		return format.format(date);
	}
}
