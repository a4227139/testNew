package com.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class testQuartz {

	public static void main(String[] args) {
		//JobDetail 需要的3个元素 ：Job实现类、jobName、groupName
		JobDetail alarm=JobBuilder.newJob(alarmClock.class).withIdentity("alarm").build();
		JobDetail myjob=JobBuilder.newJob(MyJob.class).withIdentity("myjob").build();
		//Trigger 需要的元素：调度方式(simple或cron)、triggerName、groupName，因为一个trigger只能对应一个job，所以可以用forJob指定
		Trigger workDay=TriggerBuilder.newTrigger().withIdentity("workDay").withSchedule(CronScheduleBuilder.cronSchedule("0 50 7 ? * MON-FRI")).forJob(alarm).build();
		Trigger weekend=TriggerBuilder.newTrigger().withIdentity("weekend").withSchedule(CronScheduleBuilder.cronSchedule("0 0 9 ? * SAT")).forJob(alarm).build();
		Trigger simple=TriggerBuilder.newTrigger().withIdentity("simple").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).withRepeatCount(5)).startNow().forJob(alarm).build();
		Trigger simple2=TriggerBuilder.newTrigger().withIdentity("simple2").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(4).withRepeatCount(5)).startNow().forJob(myjob).build();
		SchedulerFactory factory=new StdSchedulerFactory();
		try {
			Scheduler scheduler=factory.getScheduler();
			//首先调用两个参数的scheduleJob，存储jobDetail和trigger，后续调用一个参数的scheduleJob直接存储trigger
			scheduler.scheduleJob(alarm,workDay);
			scheduler.scheduleJob(weekend);
			scheduler.scheduleJob(simple);
			scheduler.scheduleJob(myjob,simple2);
			scheduler.start();
			Thread.sleep(60*1000);
			scheduler.shutdown(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
