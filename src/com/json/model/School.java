package com.json.model;

public class School{
	
	String name;
	String location;
	//jackson��Ҫʹ���޲ι��캯�������д���вεģ�java�Ͳ��ṩĬ�ϵ��޲ι����ˣ�����Ҫ��ʾ����
	public School() {}
	
	public School(String name, String location) {
		super();
		this.name = name;
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "School [name=" + name + ", location=" + location + "]";
	}
	
}
