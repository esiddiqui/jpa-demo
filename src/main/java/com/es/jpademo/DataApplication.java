package com.es.jpademo;

import com.es.jpademo.department.Department;
import com.es.jpademo.department.DepartmentRepository;
import com.es.jpademo.employee.Employee;
import com.es.jpademo.employee.EmployeeRepository;
import com.sun.tools.javac.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.stream.IntStream;

@SpringBootApplication
public class DataApplication implements CommandLineRunner {


    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    private Logger logger = LoggerFactory.getLogger(DataApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DataApplication.class, args);
	}


	public void run(String... args) {

        logger.debug("******* Insert Department Data ******");
        Department engineering = this.departmentRepository.save(new Department("Engineering","20002"));
        Department product = this.departmentRepository.save(new Department("Product","20003"));
        Department sales = this.departmentRepository.save(new Department("Sales","41000"));

        logger.debug("******* Insert Employee Data ******");
        IntStream.rangeClosed(0,5).forEach(id ->
                this.employeeRepository.save(new Employee("Jonh"+id,"Cena"+id,engineering, 40)));

        IntStream.rangeClosed(0,5).forEach(id ->
                this.employeeRepository.save(new Employee("Homer"+id,"Simpson"+id,product,40)));

        IntStream.rangeClosed(0,5).forEach(id ->
                this.employeeRepository.save(new Employee("Jack"+id,"Ryan"+id,sales,40)));


        logger.debug("Employees:" );
        this.employeeRepository.findAll().forEach(employee -> {
            logger.debug(employee.toString());
        });


        logger.debug("Departments:" );
        this.departmentRepository.findAll().forEach(department -> {
            logger.debug(department.toString());
            logger.debug("Number of employess: {}",department.getEmployeeList().size());
        });



    }

}
