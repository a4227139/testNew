package com.collection;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class testDelayQueue<K, V> {

	public ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<K, V>();
	public DelayQueue<DelayedItem<K>> queue = new DelayQueue<DelayedItem<K>>();

	public void put(K k, V v, long liveTime) {
		V vOld = cache.put(k, v);
		DelayedItem<K> tmpItem = new DelayedItem<K>(k, liveTime);
		// 如果之前有值，删掉旧值，再插入，相当于更新liveTime
		if (vOld != null) {
			queue.remove(tmpItem);
		}
		queue.put(tmpItem);
	}

	// 启动线程去检查超时，并将该线程设为守护线程
	public testDelayQueue() {
		Thread t = new Thread() {
			@Override
			public void run() {
				dameonCheckOverdueKey();
			}
		};
		t.setDaemon(true);
		t.start();
	}

	public void dameonCheckOverdueKey() {
		/*
		 * while (true) { DelayedItem<K> delayedItem =
		 * queue.poll();//如果getDelay大于0则得到null，相当于定时100ms检查队列头部 if (delayedItem
		 * != null) { cache.remove(delayedItem.getValue());
		 * System.out.println(System.currentTimeMillis()+" remove "+delayedItem.
		 * getValue() +" from cache"); } try { Thread.sleep(100); } catch
		 * (Exception e) { } }
		 */

		while (true) {
			DelayedItem<K> delayedItem;
			try {
				delayedItem = queue.take();// 阻塞到getDelay小于0
				if (delayedItem != null) {
					cache.remove(delayedItem.getValue());
					System.out.println(System.currentTimeMillis() + " remove " + delayedItem.getValue() + " from cache");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Random random = new Random();
		int cacheNumber = 10;
		int liveTime = 0;
		testDelayQueue<String, Integer> cache = new testDelayQueue<String, Integer>();

		for (int i = 0; i < cacheNumber; i++) {
			liveTime = random.nextInt(3000);
			System.out.println(i + "  " + liveTime);
			cache.put(i + "", i, liveTime);
		}
		Thread.sleep(300);
		cache.put(3 + "", 3, 2000);
		System.out.println(3 + "  " + 2000);
		Thread.sleep(3000);
		System.out.println();
	}

}

class DelayedItem<T> implements Delayed {

	private T t;
	private long liveTime;
	private long removeTime;

	public DelayedItem(T t, long liveTime) {
		this.setValue(t);
		this.liveTime = liveTime;
		this.removeTime = liveTime + System.currentTimeMillis();
	}

	@Override
	public int compareTo(Delayed o) {
		if (o == null)
			return 1;
		if (o == this)
			return 0;
		if (o instanceof DelayedItem) {
			DelayedItem<T> tmpDelayedItem = (DelayedItem<T>) o;
			if (liveTime > tmpDelayedItem.liveTime) {
				return 1;
			} else if (liveTime == tmpDelayedItem.liveTime) {
				return 0;
			} else {
				return -1;
			}
		}
		long diff = getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
		return diff > 0 ? 1 : diff == 0 ? 0 : -1;
	}

	// getDelay()方法返回0或者小于0的时候才能将其出列。
	@Override
	public long getDelay(TimeUnit unit) {
		return removeTime - System.currentTimeMillis();
	}

	public T getValue() {
		return t;
	}

	public void setValue(T t) {
		this.t = t;
	}

	@Override
	public int hashCode() {
		return t.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof DelayedItem) {
			return object.hashCode() == hashCode() ? true : false;
		}
		return false;
	}

}