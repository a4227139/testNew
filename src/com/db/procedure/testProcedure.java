package com.db.procedure;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class testProcedure {

	public static void main(String[] args) {
        String resource = "config.xml";
        InputStream is = testProcedure.class.getResourceAsStream(resource);
        //String resourceRoot="com/procedure/config.xml";
        //InputStream is = testProcedure.class.getClassLoader().getResourceAsStream(resourceRoot);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = sessionFactory.openSession();
        Map<String,Object> paramMap =new HashMap<>();
        paramMap.put("beginDate", "2016-03-01");
        paramMap.put("endDate", "2016-04-01");
        paramMap.put("totalAmount", 0);
        session.selectOne("com.procedure.getPurchaseTotalAmount", paramMap);
        BigDecimal totalAmount=(BigDecimal) paramMap.get("totalAmount");
        System.out.println("totalAmount:"+totalAmount);
	}
}
