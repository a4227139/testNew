package com.thread.copyOnWrite;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * 
 *多线程时光使用iterator.remove并不能解决问题。因为remove会修改本线程的iterator.expectedModCount和堆中的list.modCount，
 *但另外的线程的iterator.expectedModCount却没改变，导致expectedModCount和modCount差异，抛异常。
 */
public class multiThread {

	static ArrayList<Integer> list = new ArrayList<Integer>();
    public static void main(String[] args)  {
        list.add(1);list.add(2);list.add(3);list.add(4);list.add(5);
        Thread thread1 = new Thread(){
            public void run() {
                Iterator<Integer> iterator = list.iterator();
                while(iterator.hasNext()){
                    Integer integer = iterator.next();
                    System.out.println(integer);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        };
        Thread thread2 = new Thread(){
            public void run() {
                Iterator<Integer> iterator = list.iterator();
                while(iterator.hasNext()){
                    Integer integer = iterator.next();
                    if(integer==2)
                        iterator.remove(); 
                }
            };
        };
        thread1.start();
        thread2.start();
    }
}
