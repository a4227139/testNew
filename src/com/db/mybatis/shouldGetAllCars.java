package com.db.mybatis;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.db.procedure.testProcedure;

class testAssociation {
	@Test
	public void shouldGetAllCars() {
		String resource = "config.xml";
		InputStream is = testAssociation.class.getResourceAsStream(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		SqlSession sqlSession = sessionFactory.openSession();
		try {
			 sqlSession.getMapper(Mapper.class);
			List<Car> cars = mapper.getCars();
			Assert.assertEquals(4, cars.size());
			Assert.assertEquals("VW", cars.get(0).getType());
			Assert.assertNotNull(cars.get(0).getEngine());
			Assert.assertNull(cars.get(0).getBrakes());
			Assert.assertEquals("Opel", cars.get(1).getType());
			Assert.assertNull(cars.get(1).getEngine());
			Assert.assertNotNull(cars.get(1).getBrakes());
		} finally {
			sqlSession.close();
		}
	}
}
