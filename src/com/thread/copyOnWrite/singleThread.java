package com.thread.copyOnWrite;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * 
 *���߳�ʱʹ��iterator.remove���Ա���ConcurrentModificationException
 */
public class singleThread {

	public static void main(String[] args)  {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);list.add(2);list.add(3);list.add(4);list.add(5);
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            Integer integer = iterator.next();
            if(integer==2)
                iterator.remove();   //list.remove(2)���쳣
            System.out.println(integer);
        }
    }
}
