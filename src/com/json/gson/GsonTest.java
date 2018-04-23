package com.json.gson;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.json.model.Book;
import com.json.model.Person;
import com.json.model.School;
/**
 * 
 * gson默认不序列化为null的字段
 *
 */
public class GsonTest {

	public static void main(String[] args) {
		Person person=getPerson();
		Gson gson=new GsonBuilder()
				.serializeNulls()
				.registerTypeAdapter(Date.class, new DateDeserializer())
				.registerTypeAdapter(Book.class, new BookTypeAdapter())
				.setDateFormat("yyyy-MM-dd")
				.create();
		gson.toJson(person,System.out);
		person=gson.fromJson(new InputStreamReader(GsonTest.class.getResourceAsStream("/com/json/model/person.json")), Person.class);
		System.out.println("\n"+person);
		//注意new TypeToken<List<Person>>(){}.getType() 两层嵌套的泛型
		List<Person> lstPerson=gson.fromJson(new InputStreamReader(GsonTest.class.getResourceAsStream("/com/json/model/personlst.json")), new TypeToken<List<Person>>(){}.getType());
		person=lstPerson.get(0);
		System.out.println(person);
		Book book=new Book("Spring","123",new String[]{"XX","YY"});
		gson.toJson(book,System.out);
		book=gson.fromJson(new InputStreamReader(GsonTest.class.getResourceAsStream("/com/json/model/book.json")), Book.class);
		System.out.println("\n"+book);
	}
	
	public static Person getPerson(){
		Person person=new Person();
		person.setName("wwh");
		person.setAge(24);
		person.setSecret("...");
		Date birthdate=new Date(93, 6, 20);
		person.setBirthdate(birthdate);
		List<School> education=new ArrayList<>();
		School highSchool=new School("GGGZ", "GXGG");
		School college = new School("GXU", "GXNN");
		education.add(highSchool);
		education.add(college);
		person.setEducation(education);
		Map<String,String> family = new HashMap<>();
		family.put("father", "wkm");
		family.put("mother", "phm");
		person.setFamily(family);
		return person;
	}
}
