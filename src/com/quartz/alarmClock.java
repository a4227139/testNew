package com.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class alarmClock implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String name=context.getJobDetail().toString();
		String data=context.getJobDetail().getJobDataMap().getString("data");
		String triggerName=context.getTrigger().getJobDataMap().getString("name");
		System.out.println(name+" ding ding ding..."+data+" by "+triggerName);
	}

}
