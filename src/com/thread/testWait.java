package com.thread;

/** 
 *  
 * @author wuwenhai 
 * @since JDK1.6 
 * @history 2017-8-3 wuwenhai �½� 
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
            Thread.sleep(500);//����˴����1500����Ϊsleepû��ռ������wait������1000ms����Զ��ٴλ����Ȼ��������ִ�С�  
            synchronized(i){  
                Thread.sleep(1500);  
                i.notify();//���wait���˳�ʱʱ�䣬��������notify��wait�����Զ�������������þ����ע�ͣ���Ӱ�������������waitû�����ó�ʱʱ�䣬�þ������ڣ�����waitThread���ڴ�������״̬��  
                System.out.println("notifyThread "+(System.currentTimeMillis()-start)+" done");  
            }  
        } catch (InterruptedException e1) {  
            e1.printStackTrace();  
        }          
    }  
}  
