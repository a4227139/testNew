package com.json.model;

public class School{
	
	String name;
	String location;
	//jackson需要使用无参构造函数，如果写了有参的，java就不提供默认的无参构造了，所以要显示声明
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
