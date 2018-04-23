package com.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		System.out.println(format.format(new Date()) + ": doing something...");
	}
}
