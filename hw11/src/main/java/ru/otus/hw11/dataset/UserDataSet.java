package ru.otus.hw11.dataset;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserDataSet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age", nullable = false, length = 3)
    private int age;

    public UserDataSet() {
    }

    public UserDataSet(String name, int age) {
        this.id = -1;
        this.name = name;
        this.age = age;
    }

    public UserDataSet(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDataSet:{id = " + id + ", name = " + name + ", age = " + age + "}";
    }
}
