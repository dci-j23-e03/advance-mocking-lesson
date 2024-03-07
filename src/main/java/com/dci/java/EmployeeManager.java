package com.dci.java;

public class EmployeeManager {
	
	public static void main(String[] args) {
		EmployeeDao employeeDao1 = new EmployeeDao();
		EmployeeManager EM = new EmployeeManager(employeeDao1);
		
		// load existing
		System.out.println("Emp List: "+ EM.employeeDao.getAll());
		EM.employeeDao.sortByName();
		System.out.println("Sorted Emp List: "+ EM.employeeDao.getAll());
		
		// add new one
		Employee em = new Employee(-1);
		em.setEmail("a@b.com");
		em.setName("RP");
		EM.addEmployee(em);

		// reprint
		System.out.println("Reprint Emp List: "+ EM.employeeDao.getAll());
		EM.employeeDao.sortByName();
		System.out.println("Reprint Sorted Emp List: "+ EM.employeeDao.getAll());
		
	}
	
	private EmployeeDao employeeDao;
	private EmployeeIdGenerator idGenerator;

	public EmployeeManager(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
		this.employeeDao.initialize();
		this.setEmployeeIdGenerator(employee -> employee.setId(Math.round(Math.random() * 100)));
	}

	public boolean addEmployee(Employee employee) {
		Employee existing = this.employeeDao.getById(employee.getId());
		if (existing == null) {
			this.idGenerator.generate(employee);
			boolean saved = this.employeeDao.save(employee);
			System.out.println("saved: " + saved);
			return saved;
		} else {
			System.out.println("existing");
			return false;
		}
	}

	public void setEmployeeIdGenerator(EmployeeIdGenerator generator) {
		this.idGenerator = generator;
	}

}
