package com.thread.executor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * InvokeAll方法处理一个任务的容器（collection），并返回一个Future的容器。两个容器具有相同的结构：
 * invokeAll将Future添加到返回的容器中，这样可以使用任务容器的迭代器，从而调用者可以将它表现的Callable与Future 关联起来。
 * 当所有任务都完成时、调用线程被中断时或者超过时限时，限时版本的invokeAll都会返回结果。 超过时限后，任务尚未完成的任务都会被取消。
 * 
 */
public class testInvokeAllWithTime {
	// 固定大小的线程池，同时只能接受5个任务
	static ExecutorService mExecutor = Executors.newFixedThreadPool(5);

	private class QuoteTask implements Callable<BigDecimal> {
		public final double price;
		public final int num;

		public QuoteTask(double price, int num) {
			this.price = price;
			this.num = num;
		}

		@Override
		public BigDecimal call() throws Exception {
			Random r = new Random();
			long time = (r.nextInt(10) + 1) * 1000;
			Thread.sleep(time);
			BigDecimal d = BigDecimal.valueOf(price * num).setScale(2);
			System.out.println("耗时：" + time / 1000 + "s,单价是：" + price + ",人数是："
					+ num + "，总额是：" + d);
			return d;
		}
	}

	
	public void getRankedTravelQuotes() throws InterruptedException {
		List<QuoteTask> tasks = new ArrayList<QuoteTask>();
		// 模拟10个计算旅游报价的任务
		for (int i = 1; i <= 20; i++) {
			tasks.add(new QuoteTask(200, i));
		}

		/**
		 * 使用invokeAll方法批量提交限时任务任务 预期15s所有任务都执行完,没有执行完的任务会自动取消
		 * 
		 */
		List<Future<BigDecimal>> futures = mExecutor.invokeAll(tasks, 15,
				TimeUnit.SECONDS);
		// 报价合计集合
		List<BigDecimal> totalPriceList = new ArrayList<BigDecimal>();
		Iterator<QuoteTask> taskIter = tasks.iterator();
		for (Future<BigDecimal> future : futures) {
			QuoteTask task = taskIter.next();
			try {
				totalPriceList.add(future.get());
			} catch (ExecutionException e) {
				// 返回计算失败的原因
				// totalPriceList.add(task.getFailureQuote(e.getCause()));
				totalPriceList.add(BigDecimal.valueOf(-1));
				System.out.println("任务执行异常,单价是" + task.price + "，人数是："+ task.num);
			} catch (CancellationException e) {
				// totalPriceList.add(task.getTimeoutQuote(e));
				totalPriceList.add(BigDecimal.ZERO);
				System.out.println("任务超时,取消计算,单价是" + task.price + "，人数是："+ task.num);
			}
		}
		for (BigDecimal bigDecimal : totalPriceList) {
			System.out.print(bigDecimal+",");
		}
		mExecutor.shutdown();
	}

	public static void main(String[] args) {
		try {
			testInvokeAllWithTime it = new testInvokeAllWithTime();
			it.getRankedTravelQuotes();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}