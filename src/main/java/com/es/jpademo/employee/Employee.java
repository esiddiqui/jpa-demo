package com.es.jpademo.employee;


import com.es.jpademo.department.Department;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

@Entity
@Table(name="employee")
public class Employee {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @JoinColumn(name = "department_id") //owner, & has a FK for department.id
    @ManyToOne(fetch= FetchType.EAGER, targetEntity = Department.class)
    private Department department;

    @Column(name="age")
    private int age;


    public Employee() {
    }

    public Employee(String firstName, String lastName, Department department, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.age = age;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department=" + department +
                ", age=" + age +
                '}';
    }

}
