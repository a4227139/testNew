package com.normal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testRegex {

	public static void main(String[] args) {
		Pattern p = Pattern.compile("([a-z]+)(\\d+)");
		Matcher m = p.matcher("aaa2223bb2op");
		while(m.find()){
			System.out.println("m.groupCount():"+m.groupCount()); //返回当前匹配到的字符串的组个数
			System.out.println("m.group():"+m.group());    //返回当前匹配到的字符串
			System.out.println("m.group(1):"+m.group(1));   //返回第一组匹配到的子字符串 
			System.out.println("m.group(2):"+m.group(2));   //返回第二组匹配到的子字符串 
		}
	}
}
