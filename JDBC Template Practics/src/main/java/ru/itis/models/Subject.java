package ru.itis.models;

/**
 * 08.07.2021
 * JavaLab Summer Practice
 *
 * @author Kristina Vydrenkova
 */

public class Subject {
    private Integer id;
    private String name;
    private String time;
    private Integer courseId;

    public Subject(String name, String time, Integer courseId) {
        this.name = name;
        this.time = time;
        this.courseId = courseId;
    }

    public Subject(Integer id, String name, String time, Integer courseId) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.courseId = courseId;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }


    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", course_id=" + courseId +
                '}';
    }
}
