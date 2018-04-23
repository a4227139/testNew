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
		// 1������JobDetial����
		JobDetail jobDetail = new JobDetail();

		// ���ù�����
		jobDetail.setJobClass(MyJob.class);
		jobDetail.setName("MyJob_1");
		jobDetail.setGroup("JobGroup_1");
		//2.0���д��
		JobDetail myJob = JobBuilder.newJob(MyJob.class).withIdentity("MyJob_1", "JobGroup_1").build();  
        
		// 2������Trigger����
		SimpleTrigger strigger = new SimpleTrigger();
		strigger.setName("Trigger_1");
		strigger.setGroup("Trigger_Group_1");
		strigger.setStartTime(new Date());
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis() + 1000 * 5L);
		// �����ظ�ֹͣʱ�䣬���ʱ�䵽�˵��ظ�����δ�������쳣
		strigger.setEndTime(c.getTime());
		//���ٸ�Trigger����
		strigger.setFireInstanceId("Trigger_1_id_001");
		// �����ظ����ʱ��
		strigger.setRepeatInterval(1000 * 1L);
		// �����ظ�ִ�д���
		strigger.setRepeatCount(3);
		
		Trigger strigger2 = TriggerBuilder.newTrigger().withIdentity("Trigger_1", "Trigger_Group_1").startAt(new Date()).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(15).repeatForever()).build();
		
		CronTrigger cornTrigger=new CronTrigger("cronTrigger","triggerGroup");  
		//ִ�й�����ʽ(�ֱ����롢�֡�Сʱ���ա��¡����ڡ���(��ѡ��ǰ6��ѡ))��������ÿ������10:15ִ��
        cornTrigger.setCronExpression("0 15 22 * * ? *");  
        
        Trigger cornTrigger2=TriggerBuilder.newTrigger().withIdentity("cronTrigger","triggerGroup").withSchedule(CronScheduleBuilder.cronSchedule("0 15 22 * * ? *")).build();
		// 3������Scheduler���󣬲�����JobDetail��Trigger����
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = null;
		try {
			scheduler = sf.getScheduler();
			//4������ҵ�ʹ�����ע�ᵽ���������  
			scheduler.scheduleJob(jobDetail, strigger);
			//ִ���������رյȲ���
			scheduler.start();
			// �رյ�����
			scheduler.shutdown(true);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
