package com.json.jackson;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.json.model.Person;

public class JacksonTest {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper=new ObjectMapper();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(dateFormat);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//反序列化，对象里没有相应字段时不抛异常
		InputStream input= JacksonTest.class.getResourceAsStream("/com/json/model/person.json");
		Person person=objectMapper.readValue(input, Person.class);
		System.out.println(person);
		System.out.println(objectMapper.writeValueAsString(person));
		input=JacksonTest.class.getResourceAsStream("/com/json/model/personlst.json");
		List<Person> list=objectMapper.readValue(input, new TypeReference<List<Person>>(){});
		System.out.println(list.get(0));
	}
}
