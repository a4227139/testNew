package com.thread;

/** 
 *  
 * @author wuwenhai 
 * @since JDK1.6 
 * @history 2017-8-3 wuwenhai 新建 
 */  
public class testWait {  
  
    public static void main(String[] args) {  
        Integer i=new Integer(1);  
        new Thread(new waitThread(i)).start();  
        new Thread(new notifyThread(i)).start();  
    }  
}  
class waitThread implements Runnable{  
  
    Integer i;  
      
    public waitThread(Integer i) {  
        super();  
        this.i = i;  
    }  
  
    @Override  
    public void run() {  
        try {  
            synchronized(i){  
                long start =System.currentTimeMillis();  
                i.wait(1000);  
                System.out.println("waitThread "+(System.currentTimeMillis()-start)+" done");  
            }  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
      
}  
  
class notifyThread implements Runnable{  
  
    Integer i;  
      
    public notifyThread(Integer i) {  
        super();  
        this.i = i;  
    }  
  
    @Override  
    public void run() {  
        try {  
            long start =System.currentTimeMillis();  
            Thread.sleep(500);//如果此处设成1500，因为sleep没有占有锁，wait方法在1000ms后会自动再次获得锁然后解除阻塞执行。  
            synchronized(i){  
                Thread.sleep(1500);  
                i.notify();//如果wait过了超时时间，无论有无notify，wait都会自动解除阻塞，即该句可以注释，不影响结果。但是如果wait没有设置超时时间，该句必须存在，否则waitThread用于处于阻塞状态。  
                System.out.println("notifyThread "+(System.currentTimeMillis()-start)+" done");  
            }  
        } catch (InterruptedException e1) {  
            e1.printStackTrace();  
        }          
    }  
}  
