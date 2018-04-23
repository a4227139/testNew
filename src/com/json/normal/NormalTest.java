package com.json.normal;

import java.util.ArrayList;
import java.util.List;

import com.json.gson.GsonTest;
import com.json.model.Person;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class NormalTest {

	public static void main(String[] args) {
		Person person=GsonTest.initPerson();
		JSONObject jsonObject= JSONObject.fromObject(person);
		String json=jsonObject.toString();
		System.out.println(json);
		System.out.println("name:"+jsonObject.getString("name"));
		person=(Person) jsonObject.toBean(jsonObject, Person.class);
		System.out.println(person);
		List<Person> list=new ArrayList<>();
		list.add(person);
		JSONArray jsonArray=JSONArray.fromObject(list);
		json=jsonArray.toString();
		System.out.println(json);
		list= jsonArray.toList(jsonArray);
		Object[] personArray=jsonArray.toArray();
		System.out.println(list.get(0));
		System.out.println(personArray[0]);
	}
}
