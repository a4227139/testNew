package com.thread;

import com.google.common.util.concurrent.RateLimiter;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
  
  
public class testRateLimiter {  
  
    public static void main(String[] args) {  
        //0.5����һ�������ٸ�  
        RateLimiter rateLimiter = RateLimiter.create(0.5);  
        List<Runnable> tasks = new ArrayList<Runnable>();  
        for (int i = 0; i < 10; i++) {  
            tasks.add(new UserRequest(i));  
        }  
        ExecutorService threadPool = Executors.newCachedThreadPool();  
        for (Runnable runnable : tasks) {  
            System.out.println("�ȴ�ʱ�䣺" + rateLimiter.acquire());  //tryAcquire��������
            threadPool.execute(runnable);  
        }  
    }  
  
    private static class UserRequest implements Runnable {  
        private int id;  
  
        public UserRequest(int id) {  
            this.id = id;  
        }  
  
        public void run() {  
            System.out.println(id);  
        }  
    }  
  
}  