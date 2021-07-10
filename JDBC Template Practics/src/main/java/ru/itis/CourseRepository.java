package ru.itis;

import ru.itis.models.Course;

import java.util.List;
import java.util.Optional;

/**
 * 08.07.2021
 * JavaLab Summer Practice
 *
 * @author Kristina Vydrenkova
 */

public interface CourseRepository {
    Optional<Course> findCourseByName(String name);
    Optional<Course> findCourseByNameWithoutStudents(String name);
    Optional<Course> findCourseById(int id);
    Optional<Course> findCourseByIdWithoutStudents(int id);
    void save(Course course);
    void update(Course course);



}
