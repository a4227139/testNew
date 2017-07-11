package com.io.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class AppendFile {

	/**
	 * ͨ��FileOutputStream�ķ�ʽ
	 */
	public static void method1(String file, String conent) {     
        BufferedWriter out = null;     
        try {  
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));     
            out.write(conent);     
        } catch (Exception e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(out != null){  
                    out.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }     
    }     
    
    /**   
     * ׷���ļ���ʹ��FileWriter   
     *    
     * @param fileName   
     * @param content   
     */    
    public static void method2(String fileName, String content) {   
        FileWriter writer = null;  
        try {     
            // ��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�     
            writer = new FileWriter(fileName, true);     
            writer.write(content);       
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }   
    }     
    
    /**   
     * ׷���ļ���ʹ��RandomAccessFile   
     *    
     * @param fileName �ļ���   
     * @param content ׷�ӵ�����   
     */    
    public static void method3(String fileName, String content) {   
        RandomAccessFile randomFile = null;  
        try {     
            // ��һ����������ļ���������д��ʽ     
            randomFile = new RandomAccessFile(fileName, "rw");     
            // �ļ����ȣ��ֽ���     
            long fileLength = randomFile.length();     
            // ��д�ļ�ָ���Ƶ��ļ�β��     
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);      
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally{  
            if(randomFile != null){  
                try {  
                    randomFile.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }    
  

    /**
     * �ļ��ϲ�
     * @param dest
     * @param src
     */
	private static void fileMerge(String dest, String src) {
		for(int i=0;i<2;i++)
		try {
			long start =System.currentTimeMillis();
			FileInputStream  inputStream=new FileInputStream(new File(src));
			FileOutputStream outputStream=new FileOutputStream(new File(dest),true);
			byte[] buffer=new byte[1024*10];
			int count;
			while((count=inputStream.read(buffer))>0){
				outputStream.write(buffer,0,count);
			}
			/*FileChannel outChannel = new FileOutputStream(new File(dest),true).getChannel();  
	        FileChannel inChannel = new FileInputStream(new File(src)).getChannel();   
	        ByteBuffer bb = ByteBuffer.allocate(1024*10);  
	        while(inChannel.read(bb) != -1){  
	            bb.flip();  
	            outChannel.write(bb);  
	            bb.clear();  
	        }  */
			long end =System.currentTimeMillis();
			System.out.println("spend time: "+(end-start));
			inputStream.close();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
    public static void main(String[] args) {  
        try{  
            /*File file = new File("I://debugMin.txt");  
            if(file.createNewFile()){  
                System.out.println("Create file successed");  
            }  */
            //method1("I://debugMin.txt", "123");  
            //method2("I://debugMin.txt", "123");  
            //method3("I://debugMin.txt", "123");  
            fileMerge("I://debug.txt","I://debug2.txt");
        }catch(Exception e){  
            System.out.println(e);  
        }  
    }

}
