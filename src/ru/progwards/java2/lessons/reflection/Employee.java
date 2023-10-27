package ru.progwards.java2.lessons.reflection;

import java.util.Objects;

public class Employee implements Comparable<Employee> {

    private String name;
    protected int age;
    public int salary;
    public static int count;
    public static final String LABEL = "EMPLOYEE";

    public Employee() {

    }

    private Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Employee(String name, int age, int salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public int getAge(){
        return this.age;
    }

    private static void setCount(int c){
        count=count+c;
    }

    public static String printLabel(){
        return LABEL;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void increaseSalary(int salary){
        this.salary = this.salary + salary;
    }

    public void increaseSalary(){
        this.salary = this.salary + 100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return age == employee.age &&
                salary == employee.salary &&
                Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, salary);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }

    @Override
    public int compareTo(Employee o) {
        return name.compareTo(o.name);
    }
}
