package com.quartz;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SimpleTestQuartz {
	public static void main(String[] args) throws ParseException {
		// 1、创建JobDetial对象
		JobDetail jobDetail = new JobDetail();

		// 设置工作项
		jobDetail.setJobClass(MyJob.class);
		jobDetail.setName("MyJob_1");
		jobDetail.setGroup("JobGroup_1");
		//2.0后的写法
		JobDetail myJob = JobBuilder.newJob(MyJob.class).withIdentity("MyJob_1", "JobGroup_1").build();  
        
		// 2、创建Trigger对象
		SimpleTrigger strigger = new SimpleTrigger();
		strigger.setName("Trigger_1");
		strigger.setGroup("Trigger_Group_1");
		strigger.setStartTime(new Date());
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis() + 1000 * 5L);
		// 设置重复停止时间，如果时间到了但重复次数未完则抛异常
		strigger.setEndTime(c.getTime());
		//销毁该Trigger对象
		strigger.setFireInstanceId("Trigger_1_id_001");
		// 设置重复间隔时间
		strigger.setRepeatInterval(1000 * 1L);
		// 设置重复执行次数
		strigger.setRepeatCount(3);
		
		Trigger strigger2 = TriggerBuilder.newTrigger().withIdentity("Trigger_1", "Trigger_Group_1").startAt(new Date()).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(15).repeatForever()).build();
		
		CronTrigger cornTrigger=new CronTrigger("cronTrigger","triggerGroup");  
		//执行规则表达式(分别是秒、分、小时、日、月、星期、年(可选，前6必选))，以下是每天晚上10:15执行
        cornTrigger.setCronExpression("0 15 22 * * ? *");  
        
        Trigger cornTrigger2=TriggerBuilder.newTrigger().withIdentity("cronTrigger","triggerGroup").withSchedule(CronScheduleBuilder.cronSchedule("0 15 22 * * ? *")).build();
		// 3、创建Scheduler对象，并配置JobDetail和Trigger对象
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = null;
		try {
			scheduler = sf.getScheduler();
			//4、把作业和触发器注册到任务调度中  
			scheduler.scheduleJob(jobDetail, strigger);
			//执行启动、关闭等操作
			scheduler.start();
			// 关闭调度器
			scheduler.shutdown(true);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
