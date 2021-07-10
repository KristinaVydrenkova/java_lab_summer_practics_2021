package ru.itis;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.models.Course;
import ru.itis.models.Student;
import ru.itis.models.Teacher;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * 08.07.2021
 * JavaLab Summer Practice
 *
 * @author Kristina Vydrenkova
 */

public class CourseRepositoryJDBCTemplateImpl implements CourseRepository {
    private final JdbcTemplate jdbcTemplate;
    //language=SQL
    private static final String SQL_UPDATE_COURSE= "update course set name = ?, begin_date = ?, end_date = ?," +
            " teacher_id = ? where id = ?";
    //language=SQL
    private static final String SQL_INSERT_STUDENTS_TO_STUDENTS_OF_COURSE = "insert into students_of_course(course_id,student_id) values(?,?)";
    //language=SQL
    private static final String SQL_SELECT_COURSE_BY_NAME = "select * from course where name=?";
    //language-SQL
    private static final String SQL_INSERT_TEACHER = "insert into teacher_of_course(teacher_id,course_id) values(?,?)";
    //language=SQL
    private static final String SQL_SELECT_COURSE_BY_ID = "select * from course where id = ?";
    //language=SQL
    private static final String SQL_DELETE_COURSE_FROM_STUDENTS_OF_COURSE = "delete from students_of_course where course_id = ?";
    //language=SQL
    private static final String SQL_INSERT_COURSE = "insert into course(name,begin_date,end_date,teacher_id) values(?,?,?,?)";
    //language=SQL
    private static final String SQL_UPDATE_TEACHER = "update teacher_of_course set teacher_id = ? where course_id = ?";
    //language=SQL
    private static final String SQL_SELECT_STUDENTS = "select * from students_of_course s left join student s2 " +
            "on s.student_id = s2.id where s.course_id=?";
    //language=SQL
    private static final String SQL_SELECT_TEACHER = "select * from teacher where id = ?";

    public CourseRepositoryJDBCTemplateImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Course> courseRowMapper = (row, rowNumber) -> {

        int id = row.getInt("id");
        String name = row.getString("name");
        String dateOfBeginning = row.getString("begin_date");
        String dateOfEnd = row.getString("end_date");

        Teacher teacher = new Teacher();
        teacher.setId(row.getInt("teacher_id"));

        Course course = new Course(id, name, dateOfBeginning,dateOfEnd);
        course.setStudents(new ArrayList<>());
        course.setTeacher(findTeacherById(course).get());

        return course;
    };

    private final RowMapper<Teacher> teacherRowMapper = (row, rowNumber) -> {
        int id = row.getInt("id");
        String firstName = row.getString("first_name");
        String lastName = row.getString("last_name");
        Integer seniority = row.getInt("seniority");
        Teacher teacher = new Teacher(id,firstName,lastName,seniority);
        return teacher;
    };


    private final ResultSetExtractor<List<Student>> studentsRSExtractor = (resultSet -> {
        List<Student> list = new ArrayList<>();

        while (resultSet.next()) {
            Student student = new Student();
            student.setId(resultSet.getInt("student_id"));
            student.setFirstName(resultSet.getString("first_name"));
            student.setLastName(resultSet.getString("last_name"));
            student.setGroupNumber(resultSet.getInt("group_number"));
            list.add(student);
        }

        return list;
    });


    @Override
    public Optional<Course> findCourseByName(String name) {

        if(findCourseByNameWithoutStudents(name).isPresent()){
            Course course = findCourseByNameWithoutStudents(name).get();
            course.getStudents().addAll(findCourseStudents(course));
            return Optional.of(course);

        }else {
            return Optional.empty();
        }
    }

    private Optional<Teacher> findTeacherById(Course course){
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_TEACHER, teacherRowMapper, course.getId()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Course> findCourseByNameWithoutStudents(String name){
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_COURSE_BY_NAME, courseRowMapper, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Course> findCourseById(int id) {

        if(findCourseByIdWithoutStudents(id).isPresent()){
            Course course = findCourseByIdWithoutStudents(id).get();
            course.getStudents().addAll(findCourseStudents(course));
            return Optional.of(course);

        }else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Course> findCourseByIdWithoutStudents(int id){
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_COURSE_BY_ID, courseRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private List<Student> findCourseStudents(Course course){
        try {
            return jdbcTemplate.query(SQL_SELECT_STUDENTS,studentsRSExtractor, course.getId());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void save(Course course){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_COURSE, new String[] {"id"});

            statement.setString(1, course.getName());
            statement.setString(2, course.getDateOfBeginning());
            statement.setString(3,course.getDateOfEnd());
            statement.setInt(4, course.getTeacher().getId());
            return statement;
        }, keyHolder);

        course.setId(keyHolder.getKey().intValue());

        if(course.getStudents()!=null) {
            saveStudents(course);
        }
        saveTeacherOfCourse(course);
    }
    private void saveStudents(Course course){
        for(Student student: course.getStudents()) {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT_STUDENTS_TO_STUDENTS_OF_COURSE);
                statement.setInt(1, course.getId());
                statement.setInt(2, student.getId());
                return statement;
            });
        }
    }
    private void saveTeacherOfCourse(Course course){
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TEACHER);

            statement.setInt(1,course.getTeacher().getId());
            statement.setInt(2, course.getId());

            return statement;
        });
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_COURSE);
            statement.setString(1, course.getName());
            statement.setString(2, course.getDateOfBeginning());
            statement.setString(3,course.getDateOfEnd());
            statement.setInt(4, course.getTeacher().getId());
            statement.setInt(5,course.getId());
            return statement;
        });

        updateTeacher(course);

        deleteStudents(course);
        if(course.getStudents()!=null){
            saveStudents(course);
        }
    }
    private void updateTeacher(Course course){
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_TEACHER);
            statement.setInt(1,course.getTeacher().getId());
            statement.setInt(2,course.getId());
            return statement;
        });
    }
    private void deleteStudents(Course course){
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_COURSE_FROM_STUDENTS_OF_COURSE);
            statement.setInt(1,course.getId());
            return statement;
        });
    }

}
