package com.json.model;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.json.gson.Father;
/**
 * ���ĳ�ֶβ��뱻���л����������transient�ؼ��֣�����ȫ�������ֶ���ʹ��@Exposeע�⣬����GsonBuilder������excludeFieldsWithoutExposeAnnotation
 * jackson�ǻ���getter/setter�ģ����ֶ��޹أ�ֻҪget/set��public�ľͻ����л�������ʹ��transient���á�gson�ǻ����ֶεģ�ʹ�÷�����ơ�
 */
public class Person extends Father{

	String name;
	int age;
	@SerializedName(value="id-card",alternate={"IdCard","i.d.card"})//gson��
	@JsonProperty("i.d.card")//jackson��
	String id;
	Date birthdate;
	List<School> education;
	Map<String, String> family;
	List<File> lstFile;
	@JsonIgnore //jackson�ã�ʹ�ø�ע����Ҫ����transient�ؼ���ȥ�������Ч
	private transient String secret;
	
	public List<File> getLstFile() {
		return lstFile;
	}
	public void setLstFile(List<File> lstFile) {
		this.lstFile = lstFile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<School> getEducation() {
		return education;
	}
	public void setEducation(List<School> education) {
		this.education = education;
	}
	public Map<String, String> getFamily() {
		return family;
	}
	public void setFamily(Map<String, String> family) {
		this.family = family;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", id=" + id + ", birthdate=" + birthdate + ", education="
				+ education + ", family=" + family + ", lstFile=" + lstFile + ", secret=" + secret + ", fatherName="
				+ fatherName + "]";
	}		
	
}
