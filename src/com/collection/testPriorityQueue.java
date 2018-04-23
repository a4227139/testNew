package com.collection;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
 
public class testPriorityQueue {
 
    public static void main(String[] args) {
 
        //优先队列自然排序示例
        Queue<Integer> integerPriorityQueue = new PriorityQueue<>(7);
        Random rand = new Random();
        for(int i=0;i<7;i++){
            integerPriorityQueue.add(new Integer(rand.nextInt(100)));
        }
        for(int i=0;i<7;i++){
            Integer in = integerPriorityQueue.poll();
            System.out.println("Processing Integer:"+in);
        }
        
        Queue<String> stringPriorityQueue = new PriorityQueue<>(7);
        for(int i=0;i<7;i++){
        	stringPriorityQueue.add(rand.nextInt(100)+"");
        }
        for(int i=0;i<7;i++){
        	String s = stringPriorityQueue.poll();
            System.out.println("Processing String:"+s);
        }
 
        //优先队列使用示例
        Queue<Customer> customerPriorityQueue = new PriorityQueue<>(7);
        addDataToQueue(customerPriorityQueue);
        pollDataFromQueue(customerPriorityQueue);
 
    }
 
    //用于往队列增加数据的通用方法
    private static void addDataToQueue(Queue<Customer> customerPriorityQueue) {
        Random rand = new Random();
        for(int i=0; i<7; i++){
            int id = rand.nextInt(100);
            customerPriorityQueue.add(new Customer(id, "Pankaj "+id));
        }
    }
 
    //用于从队列取数据的通用方法
    private static void pollDataFromQueue(Queue<Customer> customerPriorityQueue) {
        while(true){
            Customer cust = customerPriorityQueue.poll();
            if(cust == null) break;
            System.out.println("Processing Customer with ID="+cust.getId());
        }
    }
}

class Customer implements Comparable<Customer>{
	 
    private int id;
    private String name;
 
    public Customer(int i, String n){
        this.id=i;
        this.name=n;
    }
 
    public int getId() {
        return id;
    }
 
    public String getName() {
        return name;
    }

	@Override
	public int compareTo(Customer o) {
		return this.id-o.id;
	}
 
}

 