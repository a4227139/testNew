package com.normal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testRegex {

	public static void main(String[] args) {
		Pattern p = Pattern.compile("([a-z]+)(\\d+)");
		Matcher m = p.matcher("aaa2223bb2op");
		while(m.find()){
			System.out.println("m.groupCount():"+m.groupCount()); //���ص�ǰƥ�䵽���ַ����������
			System.out.println("m.group():"+m.group());    //���ص�ǰƥ�䵽���ַ���
			System.out.println("m.group(1):"+m.group(1));   //���ص�һ��ƥ�䵽�����ַ��� 
			System.out.println("m.group(2):"+m.group(2));   //���صڶ���ƥ�䵽�����ַ��� 
		}
	}
}
