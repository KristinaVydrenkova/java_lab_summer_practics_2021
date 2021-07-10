package ru.itis.models;

import java.util.List;

/**
 * 08.07.2021
 * JavaLab Summer Practice
 *
 * @author Kristina Vydrenkova
 */

public class Course {
    private Integer id;
    private String name;
    private String dateOfBeginning;
    private String dateOfEnd;
    private Teacher teacher;
    private List<Student> students;

    public Course(){}
    public Course(Integer id, String name, String dateOfBeginning, String dateOfEnd) {
        this.id = id;
        this.name = name;
        this.dateOfBeginning = dateOfBeginning;
        this.dateOfEnd = dateOfEnd;
    }

    public Course(String name, String dateOfBeginning, String dateOfEnd) {
        this.name = name;
        this.dateOfBeginning = dateOfBeginning;
        this.dateOfEnd = dateOfEnd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBeginning() {
        return dateOfBeginning;
    }

    public void setDateOfBeginning(String dateOfBeginning) {
        this.dateOfBeginning = dateOfBeginning;
    }

    public String getDateOfEnd() {
        return dateOfEnd;
    }

    public void setDateOfEnd(String dateOfEnd) {
        this.dateOfEnd = dateOfEnd;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> studentId) {
        this.students = studentId;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBeginning='" + dateOfBeginning + '\'' +
                ", dateOfEnd='" + dateOfEnd + '\'' +
                ", teacher" + teacher +
                ", students=" + students +
                '}';
    }

}
