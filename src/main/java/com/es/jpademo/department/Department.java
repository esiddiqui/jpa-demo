package com.es.jpademo.department;


import com.es.jpademo.employee.Employee;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="department")
public class Department {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Basic(optional=false)
    @Column(name="department_name")
    private String name;

    @Column(name ="department_code")
    private String code;

    //Address address TBD
    //Employee leader TBD

    @OneToMany(targetEntity = Employee.class,
            mappedBy = "department",   //this is the inverse, and references the join column field Employee::department
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true )
    private List<Employee> employeeList;

    public Department() {
    }

    public Department(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", employeeList(size)=" + employeeList.size() +
                '}';
    }
}
