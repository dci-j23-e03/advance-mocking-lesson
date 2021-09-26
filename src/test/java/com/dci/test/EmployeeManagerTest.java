package com.dci.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import com.dci.java.Employee;
import com.dci.java.EmployeeDao;
import com.dci.java.EmployeeManager;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeManagerTest {
	@InjectMocks
    private EmployeeManager empManager;
	
    @Mock
    private EmployeeDao employeeDao;
    private long empID = 2;
    private Employee employee = new Employee(empID);
    
    @Captor
    private ArgumentCaptor<Employee> employeeCaptor;

    @Test
    public void addEmployeeWhenExists() {
        // employee.setId(-1); // 1
        when(employeeDao.getById(empID)).thenReturn(employee); // 2
        boolean result = empManager.addEmployee(employee); // 3
        
        assertFalse(result); // 4
        verify(employeeDao, never()).save(employee); // 5
    }

    @Test
    public void addEmployeeCheckEmployeeId() {
    	long newEmpID = 2021; // 0
    	empManager.setEmployeeIdGenerator(employee -> employee.setId(newEmpID)); // 1
    	
        when(employeeDao.getById(empID)).thenReturn(null); // 2
        when(employeeDao.save(employee)).thenReturn(true); // 3
        boolean result = empManager.addEmployee(employee); // 4
        assertTrue(result); // 5
        
        verify(employeeDao).save(employeeCaptor.capture()); // 6
        assertEquals(newEmpID, employeeCaptor.getValue().getId()); // 7
    }
}
