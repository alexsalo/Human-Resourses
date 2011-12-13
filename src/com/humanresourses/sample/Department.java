package com.humanresourses.sample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class Department implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private Employee head;
	private List<Employee> stuff = new ArrayList<Employee>();
	
	public Department(String name){
		this.setName(name);
	}
	
	public Employee getEmployeeAtIndex(int index){
		return stuff.get(index);
	}
	
	public void addEmployee(Employee employee){
		stuff.add(employee);
	}
	
	public void printAllEmployees(){
		System.out.println("The head of department is: " + head.getFini());
		System.out.println("The stuff:");
		for (Employee em : stuff){
			final StringBuilder sb = new StringBuilder();
			sb.append(em.getFIO());
			sb.append(" ");
			sb.append(em.getPosition().toString());
			System.out.println(sb.toString());
		}
	}
	
	public void putOnFile(String path) throws IOException{
		FileOutputStream fos = new FileOutputStream("out.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this);
		oos.close();
	}
	
	public Department getFromFile(String path) throws IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream("out.dat");
		ObjectInputStream oin = new ObjectInputStream(fis);
		Department tmp = (Department) oin.readObject();
		return tmp;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Employee getHead() {
		return head;
	}

	public void setHead(Employee head) {
		this.head = head;
	}
	public int size(){
		return stuff.size();
	}
	public void setEmployeeAt(int index, Employee em){
		stuff.set(index, em);
	}
	public void remove(int index){
		stuff.remove(index);
	}
	
}
