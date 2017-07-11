package com.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 读文件，统计单词数
 * @author wuwenhai
 * @since JDK1.6
 * @history 2017-7-11 wuwenhai 新建
 */
public class WordsCountMappedByteBuffer {

	static final int BUFFER_SIZE = 1024*10;
	public static Map<String,Integer> map=new HashMap<String,Integer>();
	static byte[] left=new byte[0];
	static final int LF = 10;//换行符  ASCII
	static final int CR = 13;//回车符  ASCII
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		long start=System.currentTimeMillis();
		FileChannel channel= new FileInputStream(new File("I://debug.txt")).getChannel();
		MappedByteBuffer buffer=channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
		byte[] byteArray=new byte[BUFFER_SIZE];
		while(buffer.hasRemaining()){
			if(buffer.limit()-buffer.position()>BUFFER_SIZE){
				buffer.get(byteArray);
				deal(byteArray,false);
        	}else{
        		byte[] byteArray2=new byte[buffer.limit()-buffer.position()];
        		buffer.get(byteArray2);  
        		deal(byteArray2,true);
        	}
		}		
		long end=System.currentTimeMillis();
		System.out.println("spend time :"+(end-start));//如果想看MappedByteBuffer的读取速度，可以将两个deal注释掉
		System.out.println("count of total words :"+map.size());//单词总数
		//show();
		findTop(10);
		//sortTreeMapShow();
	}
	/**
	 * 生硬地截取BUFFER_SIZE字节，必然会导致开头和末尾有问题，所以可以将byteArray分为2部分来处理。
	 * 从末尾逆向寻找最后一个分隔符坐标endIndex，将endIndex-末尾作为第二部分。
	 * 将0-endIndex作为第一部分。
	 * 每次deal的数据是：上一次deal的第二部分+本次deal的第一部分，同时保存本次deal的第二部分，以供下次deal使用。
	 * @param byteArray
	 * @param EOF 
	 */
	public static void deal(byte[] byteArray,boolean EOF){
		String input;
		if(!EOF&&findLastDelimiter(byteArray)>0){
			int endIndex=findLastDelimiter(byteArray);
			byte[] firstPart=Arrays.copyOfRange(byteArray, 0,endIndex);
			byte[] secondPart=Arrays.copyOfRange(byteArray, endIndex,byteArray.length);
			byte[] mergeArray=arrayMerge(left,firstPart);
			input=new String(mergeArray);
			left=secondPart;
		}else if(!EOF&&findLastDelimiter(byteArray)<0){//如果读取的字节数组里面没有分隔符，则该次读取的整个字节数组与left合并后保存至left供下次deal使用。
			byte[] mergeArray=arrayMerge(left,byteArray);
			left=mergeArray;
			return;
		}else{//如果是读到文件末尾了，则该次读取的整个字节数组与left合并后并处理
			byte[] mergeArray=arrayMerge(left,byteArray);
			input=new String(mergeArray);
		}
		
		Pattern pattern=Pattern.compile("[A-Z]?[a-z]+");
		Matcher matcher=pattern.matcher(input);
		while (matcher.find()) {
			String word=matcher.group();
			if(map.containsKey(word)){
				map.put(word, map.get(word)+1);
			}else{
				map.put(word, 1);
			}
		}
	}

	public static int findLastDelimiter(byte[] byteArray){
		for(int i=byteArray.length-1;i>=0;i--){
			if(byteArray[i]==LF||byteArray[i]==CR){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 数组合并
	 * @param first
	 * @param second
	 * @return
	 */
	public static byte[] arrayMerge(byte[] first,byte[] second){
		byte[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);  
		return result;
	}
	
	/**
	 * 遍历显示
	 */
	public static void show(){
		for(Map.Entry<String, Integer> entry:map.entrySet()){
			System.out.println(entry.getKey()+" : "+entry.getValue());
		}
	}
	
	/**
	 * 寻找map结果中top k的数据，不排序
	 * 思路：寻找top k其实就是寻找某个数，列表里大于这个数的个数有k个。
	 * @param k
	 */
	public static void findTop(int k){
		int max=0,min=Integer.MAX_VALUE,mid=0;
		for(Map.Entry<String, Integer> entry:map.entrySet()){
			if(entry.getValue()>max)	max=entry.getValue();
			if(entry.getValue()<min)	min=entry.getValue();
		}
		while(max-min > 1)
		{
			mid = (max+min)/2;
			if(countBiggerThanMid(map,mid) > k){
				min = mid;
			}else if(countBiggerThanMid(map,mid) < k){
				max = mid;
			}else{
				break;
			}
		}
		for(Map.Entry<String, Integer> entry:map.entrySet()){
			if(entry.getValue()>=mid){
				System.out.println(entry.getKey()+" : "+entry.getValue());
			}
		}
	}
	
	/**
	 * 在区间中寻找比mid大的数据的个数
	 * @param map 
	 * @param mid
	 * @return
	 */
	public static int countBiggerThanMid(Map<String,Integer> map ,int mid){
		int count=0;
		for(Map.Entry<String, Integer> entry:map.entrySet()){
			if(entry.getValue()>=mid)
				count++;
		}
		return count;
	}
	
	/**
	 * 排序并显示
	 */
	public static void sortTreeMapShow(){
		ValueComparator comparator =  new ValueComparator(map);  
        TreeMap<String,Integer> treeMap = new TreeMap<String,Integer>(comparator);
        treeMap.putAll(map);  
        System.out.println("results: "+treeMap);
	}
}

class ValueComparator implements Comparator<String> {  

	Map<String, Integer> map;  
    public ValueComparator(Map<String, Integer> map) {  
        this.map = map;  
    }  
  
    public int compare(String key1, String key2) {  
        if (map.get(key1) >= map.get(key2)) {  
            return -1;  
        } else {  
            return 1;  
        } 
    }  
}  
