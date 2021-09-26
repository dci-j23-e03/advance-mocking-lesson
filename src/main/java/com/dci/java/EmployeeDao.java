package com.dci.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class EmployeeDao {
	private List<Employee> empList = new ArrayList<Employee>();

	private String loadJsonData() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("./data/employees.json"));
			StringBuffer content = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
			return content.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		}

		return "";
	}

	public void initialize() {
		this.initialize(this.loadJsonData());
		System.out.println("Initialized: " + this.empList);
	}

	public void initialize(String jsonData) {
		try {
			JSONArray emps = getJSONRecords(jsonData);
			for (Object obj : emps) {
				if (obj instanceof JSONObject) {
					JSONObject empObj = (JSONObject) obj;
					long id = Long.valueOf(empObj.get("id").toString());
					Employee emp = new Employee(id);
					emp.setName(empObj.get("name").toString());
					emp.setEmail(empObj.get("email").toString());
					this.empList.add(emp);
				}
			}
			// System.out.println(this.empList);
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private JSONArray getJSONRecords(String data) {
		JSONArray emps = new JSONArray();
		try {
			emps = (JSONArray) JSONValue.parse((data == null) ? data : this.loadJsonData());
		} catch (Exception e) {
		}
		return emps;
	}

	public boolean save(Employee employee) {
		this.empList.add(employee);
		return true;
	}

	public Employee getById(long id) {
		for (Employee emp : this.empList) {
			if (emp.getId() == id) {
				return emp;
			}
		}
		System.out.println("Emp not found with id: " + id);
		return null;
	}

	public List<Employee> getAll() {
		return this.empList;
	}
	
	public void sortByName() {
		this.getAll().sort(new EmployeeNameComparator());
	}
}