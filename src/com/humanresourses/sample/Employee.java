package com.humanresourses.sample;

import java.io.Serializable;

public final class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	private String fatherName;
	private int salary;
	private Position position;
	
	public Employee(){
	}
	
	public Employee(String name, String surname, String fatherName, int salary, Position position){
		this.setName(name);
		this.setSurname(surname);
		this.setFatherName(fatherName);
		if (salary > 0){
			this.setSalary(salary);
		}else{
			this.setSalary(0);
			System.out.println("Enter correct salary value");
		}
		this.setPosition(position);
	}

	public String getBriefCase(){
		return getFini() + " work as " + getPosition() + " earn " + getSalary() + "$ per month";
	}
	
	public String getFIO() {
		return surname + " " + name + " " + fatherName;
	}
	
	public String getFini() {
		return surname + " " + name.substring(0,1) + "." + fatherName.substring(0,1) + ".";
	}
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

}
