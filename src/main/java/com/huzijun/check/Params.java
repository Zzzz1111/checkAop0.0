package com.huzijun.check;

public class Params {
	@Check
	private String id;
	@Check
	private  String name;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
