package com.io.nio;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class IOComparison {  
    static final int BUFFER_SIZE = 1024*10;  
  
    public static void testMappedByteBuffer() throws IOException{
    	File file = new File("E:\\AllLog.log");  
    	FileChannel channel = new FileInputStream(file).getChannel();  
        MappedByteBuffer buff = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());  
        byte[] b = new byte[BUFFER_SIZE];  
        long begin = System.currentTimeMillis();  
        while (buff.remaining() > 0) {
        	if(buff.limit()-buff.position()>BUFFER_SIZE){
        		buff.get(b);
        	}else{
        		buff.get(new byte[buff.limit()-buff.position()]);  
        	}//34ms
        	//buff.get();//13ms
		}
        long end = System.currentTimeMillis();  
        System.out.println("testMappedByteBuffer time is:" + (end - begin));  
    }
    
    public static void testNormalNIO() throws IOException{
    	File file = new File("E:\\AllLog.log");  
    	FileChannel channel = new FileInputStream(file).getChannel();  
    	ByteBuffer buff = ByteBuffer.allocate(BUFFER_SIZE);   
    	long begin = System.currentTimeMillis();  
    	while (channel.read(buff) != -1) {  
    	    buff.flip();  //make ready to read
    	    //do something
    	    buff.clear();  //make ready to write
    	}  
    	long end = System.currentTimeMillis();  
    	System.out.println("testNormalNIO time is:" + (end - begin));  
    }
    
    public static void testDirectByteBuffer() throws IOException{
    	File file = new File("E:\\AllLog.log");  
    	FileChannel channel = new FileInputStream(file).getChannel();
    	ByteBuffer buff = ByteBuffer.allocateDirect(BUFFER_SIZE);   
    	long begin = System.currentTimeMillis();  
    	while (channel.read(buff) != -1) {  
    	    buff.flip();  
    	    buff.clear();  
    	}  
    	long end = System.currentTimeMillis();  
    	System.out.println("testDirectByteBuffer time is:" + (end - begin));
    }
    
    public static void testNormalIO()throws IOException{
    	File file = new File("E:\\AllLog.log");  
    	FileInputStream inputStream = new FileInputStream(file);
    	byte[] buff=new byte[BUFFER_SIZE];
    	long begin = System.currentTimeMillis();  
    	while(inputStream.read(buff)>0){//inputStream.read()会慢很多
    		
    	}
    	long end = System.currentTimeMillis();  
    	System.out.println("testNormalIO time is:" + (end - begin));
    }
    
    public static void testNormalBufferIO()throws IOException{
    	File file = new File("E:\\AllLog.log");  
    	BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file)) ;
    	byte[] buff=new byte[BUFFER_SIZE];
    	long begin = System.currentTimeMillis();  
    	while(inputStream.read(buff)>0){//inputStream.read()会快，因为其本身就预读到内置的buf[]里面了
    		
    	}
    	long end = System.currentTimeMillis();  
    	System.out.println("testNormalBufferIO time is:" + (end - begin));
    }
    
    public static void testRandomAccessFile()throws IOException{
    	File file = new File("E:\\AllLog.log");
    	RandomAccessFile raf=new RandomAccessFile(file, "r");
    	long begin = System.currentTimeMillis(); 
    	String line;
    	while((line=raf.readLine())!=null){
    		
    	}
    	long end = System.currentTimeMillis();  
    	System.out.println("testRandomAccessFile time is:" + (end - begin));
    }
    
    public static void main(String[] args) throws Exception { 
    	testNormalNIO();
    	testMappedByteBuffer();
    	testDirectByteBuffer();
    	testNormalIO();
    	testNormalBufferIO();
    	testRandomAccessFile();
    }  
}  