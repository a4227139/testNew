package com.io.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScannerTest {

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream inputStream=new FileInputStream(new File("E://AllLog.log"));
		Scanner scanner=new Scanner(inputStream);
		Map<String, Integer> wordCount=new HashMap<String, Integer>();
		Pattern pattern = Pattern.compile("[A-Z]?[a-z]+");//\\w+ª·∆•≈‰ASCII
		long start=System.currentTimeMillis();
		while(scanner.hasNext()){
			String line=scanner.nextLine();
			Matcher match=pattern.matcher(line);  
	        while(match.find()){  
	        	String word=match.group();
	            if(wordCount.containsKey(word)){
	            	int count=wordCount.get(word);
	            	wordCount.put(word, ++count);
	            }else {
	            	wordCount.put(word, 1);
				}
	        }  
			//System.out.println(line);
		}
		long end=System.currentTimeMillis();
		System.out.println("Spend Time : "+(end-start));
		for(Map.Entry<String, Integer> entry:wordCount.entrySet()){
			System.out.println(entry.getKey()+" : "+entry.getValue());
		}
	}
}
