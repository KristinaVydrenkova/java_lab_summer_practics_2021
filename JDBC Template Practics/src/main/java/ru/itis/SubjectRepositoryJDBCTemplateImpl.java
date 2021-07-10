package ru.itis;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.models.Subject;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Optional;

/**
 * 08.07.2021
 * JavaLab Summer Practice
 *
 * @author Kristina Vydrenkova
 */

public class SubjectRepositoryJDBCTemplateImpl implements SubjectRepository {

    private final JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_SELECT_SUBJECT_BY_NAME = "select * from subject where name=?";
    //language=SQL
    private static final String SQL_SELECT_SUBJECT_BY_ID = "select * from subject where id=?";
    //language=SQL
    private static final String SQL_INSERT_SUBJECT = "insert into subject(name,time,course_id) values(?,?,?)";
    //language=SQL
    private static final String SQL_UPDATE_SUBJECT= "update SUBJECT set name = ?, time = ?, course_id = ? where id = ?";

    public SubjectRepositoryJDBCTemplateImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Subject> subjectRowMapper = (row, rowNumber) -> {
        int id = row.getInt("id");
        String name = row.getString("name");
        String time = row.getString("time");
        int courseId = row.getInt("course_id");
        Subject subject = new Subject(id, name, time,courseId);
        return subject;
    };



    @Override
    public Optional<Subject> findSubjectByName(String name) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_SUBJECT_BY_NAME, subjectRowMapper, name));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Subject> findSubjectById(int id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_SUBJECT_BY_ID, subjectRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Subject subject) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_SUBJECT, new String[] {"id"});

            statement.setString(1, subject.getName());
            statement.setString(2, subject.getTime());
            statement.setInt(3,subject.getCourseId());
            return statement;
        }, keyHolder);

        subject.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Subject subject) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_SUBJECT);

            statement.setString(1, subject.getName());
            statement.setString(2, subject.getTime());
            statement.setInt(3,subject.getCourseId());
            statement.setInt(4, subject.getId());
            return statement;
        });
    }
}
