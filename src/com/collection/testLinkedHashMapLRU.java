package com.collection;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class testLinkedHashMapLRU {

	public static void main(String[] args) {  
		  
	    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(16, 0.75f, true);  
	    map.put("a", "a"); 
	    map.put("b", "b"); 
	    map.put("c", "c"); 
	    //map.put("a", "a");       
	    map.put("d", "d");  
	    map.put("e", "e");  
	   // map.put("a", "a");   
	   // map.put("b", "b");     
	    map.put("f", "f"); 
	    map.put("g", "g");
	  
	    map.get("d");  
	    for (Entry<String, String> entry : map.entrySet()) {  
	        System.out.print(entry.getValue() + ", ");  
	    }  
	    System.out.println();  
	  
	    map.get("a");  
	    for (Entry<String, String> entry : map.entrySet()) {  
	        System.out.print(entry.getValue() + ", ");  
	    }  
	    System.out.println();  
	  
	    map.get("c");  
	    for (Entry<String, String> entry : map.entrySet()) {  
	        System.out.print(entry.getValue() + ", ");  
	    }  
	    System.out.println();  
	  
	    map.get("b");
	    for (Entry<String, String> entry : map.entrySet()) {  
	        System.out.print(entry.getValue() + ", ");  
	    }  
	    System.out.println();  
	  
	    map.put("h", "h"); 
	    for (Entry<String, String> entry : map.entrySet()) {  
	        System.out.print(entry.getValue() + ", ");  
	    }  
	    System.out.println();  
	}  
}
