package com.ra2.jdbctemplate_h2.model;

public class Customer {
    private Long id;
    private String name;
    private String email;
    private int age;
    private String cicle;
    private int year_val; // renombrado para evitar conflicto con palabra reservada

    public Customer() {}

    public Customer(Long id, String name, String email, int age, String cicle, int year_val) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.cicle = cicle;
        this.year_val = year_val;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getCicle() { return cicle; }
    public void setCicle(String cicle) { this.cicle = cicle; }

    public int getYear_val() { return year_val; }
    public void setYear_val(int year_val) { this.year_val = year_val; }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", cicle='" + cicle + '\'' +
                ", year_val=" + year_val +
                '}';
    }
}
