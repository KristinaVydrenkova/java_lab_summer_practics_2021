package ru.itis;

import ru.itis.models.Subject;
import java.util.Optional;

/**
 * 08.07.2021
 * JavaLab Summer Practice
 *
 * @author Kristina Vydrenkova
 */

public interface SubjectRepository {
    Optional<Subject> findSubjectByName(String name);
    Optional<Subject> findSubjectById(int id);
    void save(Subject subject);
    void update(Subject subject);
}
