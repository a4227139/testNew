package com.redis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class InitPersonInfo {
	static SqlSession session;
	static{
		//class.getResourceAsStream默认加载和.class文件在同一目录下
		//class.getClassLoader.getResourceAsStream默认加载ClassPath根下
		InputStream is = InitPersonInfo.class.getResourceAsStream("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        session = sessionFactory.openSession();
	}
    @SuppressWarnings({ "resource", "rawtypes" })
	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
    	BufferedReader reader=new BufferedReader(new FileReader("G:\\hotel\\2000W\\last5000.txt"));
    	String line=reader.readLine();
    	int count=0;
    	List<PersonInfoVO> lstPersonInfoVO =new ArrayList<PersonInfoVO>();
    	while((line=reader.readLine())!=null){
        	String[] array=line.split(",");
        	PersonInfoVO personInfoVO=new PersonInfoVO();
        	Class clazz=PersonInfoVO.class;
        	Field[] fields= clazz.getDeclaredFields();
        	for(int i=0;i<array.length;i++){
        		fields[i].setAccessible(true);
        		fields[i].set(personInfoVO, array[i]);
        	}
        	lstPersonInfoVO.add(personInfoVO);
        	System.out.println(personInfoVO);
        	count++;
        	if(count>=500){
        		session.insert("com.redis.personInfoMapper.batchInsertPersonInfo", lstPersonInfoVO);
        		count=0;
        		lstPersonInfoVO.clear();
        	}
    	}
    	session.insert("com.redis.personInfoMapper.batchInsertPersonInfo", lstPersonInfoVO);
    	session.commit();  
    }
      
}