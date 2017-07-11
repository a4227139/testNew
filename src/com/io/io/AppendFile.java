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
	 * 通过FileOutputStream的方式
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
     * 追加文件：使用FileWriter   
     *    
     * @param fileName   
     * @param content   
     */    
    public static void method2(String fileName, String content) {   
        FileWriter writer = null;  
        try {     
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件     
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
     * 追加文件：使用RandomAccessFile   
     *    
     * @param fileName 文件名   
     * @param content 追加的内容   
     */    
    public static void method3(String fileName, String content) {   
        RandomAccessFile randomFile = null;  
        try {     
            // 打开一个随机访问文件流，按读写方式     
            randomFile = new RandomAccessFile(fileName, "rw");     
            // 文件长度，字节数     
            long fileLength = randomFile.length();     
            // 将写文件指针移到文件尾。     
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
     * 文件合并
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
