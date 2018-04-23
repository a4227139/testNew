package com.quartz;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class test {

	public static void main(String[] args) throws ParseException, SchedulerException, InterruptedException {
		JobDetail jobDetail=new JobDetail("alarmClock", "group", alarmClock.class);
		jobDetail.getJobDataMap().put("data", "DATA");
		CronTrigger triggerWork=new CronTrigger("triggerWork");
		triggerWork.setCronExpression("0/2 * * * * ?");//("* 50 7 ? * MON-FRI");
		triggerWork.getJobDataMap().put("name", "triggerWork");
		CronTrigger triggerOvertime=new CronTrigger("triggerOvertime");
		triggerOvertime.setCronExpression("0/3 * * * * ?");//("* * 9 ? * 0");
		triggerOvertime.setJobName("alarmClock");
		triggerOvertime.setJobGroup("group");
		triggerOvertime.getJobDataMap().put("name", "triggerOvertime");
		SchedulerFactory factory=new StdSchedulerFactory();
		Scheduler scheduler=factory.getScheduler();
		scheduler.scheduleJob(jobDetail, triggerWork);
		scheduler.scheduleJob(triggerOvertime);
		scheduler.start();
		Thread.sleep(10*1000);
        scheduler.pauseTrigger("triggerWork",null);
        scheduler.pauseTrigger("triggerOvertime",null);
        /*Thread.sleep(10*500);
        scheduler.resumeTrigger("triggerOvertime",null);//ֱ�ӻָ�*/
        
        scheduler.unscheduleJob("triggerWork", null);//ע��˴���trigger����Ϣ����Ϊ�Ƴ�ĳ��������
        scheduler.rescheduleJob("triggerWork", null, triggerOvertime);//�ָ���Ҫ����ָ��triggerʵ��
        /*Thread.sleep(10*1000);
        scheduler.resumeJob("alarmClock", "group");*/
        Thread.sleep(10*500);
        scheduler.deleteJob("alarmClock", "group");//�Ƴ�����������������trigger��Ȼ������ɾ��
        scheduler.shutdown();
	}
}
