package ru.itis.models;

/**
 * 08.07.2021
 * JavaLab Summer Practice
 *
 * @author Kristina Vydrenkova
 */

public class Teacher {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer seniority;

    public Teacher() {
    }

    public Teacher(Integer id, String firstName, String lastName, Integer seniority) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.seniority = seniority;
    }

    public Teacher(String firstName, String lastName, Integer seniority) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.seniority = seniority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getSeniority() {
        return seniority;
    }

    public void setSeniority(Integer seniority) {
        this.seniority = seniority;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", seniority=" + seniority +
                '}';
    }
}
