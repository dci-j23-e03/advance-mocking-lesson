package com.dci.test;

import com.dci.java.Employee;
import com.dci.java.EmployeeDao;
import com.dci.java.EmployeeIdGenerator;
import com.dci.java.EmployeeManager;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeManagerTestOurs {
    @Mock
    private EmployeeDao employeeDaoMock;
    @InjectMocks
    private EmployeeManager employeeManager;
    private long empId = 2;
    private Employee employee = new Employee(empId);

    private ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);

    @Test
    public void addEmployeeWhenExists() {
        // given
        when(employeeDaoMock.getById(empId)).thenReturn(employee);

        // when
        boolean actual = employeeManager.addEmployee(employee);

        // then
        assertFalse(actual);
        verify(employeeDaoMock, never()).save(employee);
    }

    @Test
    public void addEmployeeWhenNotExsists() {
        // given
        int newEmpId = 100;
        employeeManager.setEmployeeIdGenerator(employee -> employee.setId(newEmpId));
        when(employeeDaoMock.getById(empId)).thenReturn(null);
        when(employeeDaoMock.save(employee)).thenReturn(true);

        // when
        boolean actual = employeeManager.addEmployee(employee);

        // then
        assertTrue(actual);
        verify(employeeDaoMock).save(captor.capture());
        assertEquals(newEmpId, captor.getValue().getId());
    }
}
