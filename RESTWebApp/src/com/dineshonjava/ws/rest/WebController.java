package com.dineshonjava.ws.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.dineshonjava.ws.rest.bean.Employee;

/**
 * @author Dinesh Rajput
 *
 */

@Path("/webservice")
public class WebController {
	
	 private static Map<String, Employee> employees = new HashMap<String, Employee>();
	    
	    static {
	        
	        Employee employee1 = new Employee();
	        employee1.setEmployeeId("11111");
	        employee1.setEmployeeName("Dineh Rajput");
	        employee1.setJobType("Sr.Software Engineer");
	        employee1.setSalary(70000l);
	        employee1.setAddress("Noida");
	        employees.put(employee1.getEmployeeId(), employee1);
	        
	        Employee employee2 = new Employee();
	        employee2.setEmployeeId("22222");
	        employee2.setEmployeeName("Abhishek");
	        employee2.setJobType("Marketing");
	        employee2.setSalary(50000l);
	        employee2.setAddress("New Delhi");
	        employees.put(employee2.getEmployeeId(), employee2);
	        
	    }

	    @GET
	    @Path("/hello")
	    @Produces("text/plain")
	    public String hello(){
	        return "Hello World!!! dineshonjava";    
	    }
	    
	    @GET
	    @Path("/message/{message}")
	    @Produces("text/plain")
	    public String showMsg(@PathParam("message") String message){
	        return message;    
	    }
	    
	    @GET
	    @Path("/employees")
	    @Produces("application/xml")
	    public List<Employee> listEmployees(){
	        return new ArrayList<Employee>(employees.values());
	    }
	    
	    @GET
	    @Path("/employee/{employeeid}")
	    @Produces("application/xml")
	    public Employee getEmployee(@PathParam("employeeid")String employeeId){
	        return employees.get(employeeId);        
	    }
	    
	    @GET
	    @Path("/json/employees/")
	    @Produces("application/json")
	    public List<Employee> listEmployeesJSON(){
	        return new ArrayList<Employee>(employees.values());
	    }

	    @GET
	    @Path("/json/employee/{employeeid}")
	    @Produces("application/json")
	    public Employee getEmployeeJSON(@PathParam("employeeid")String employeeId){
	        return employees.get(employeeId);        
	    }
	 
}
