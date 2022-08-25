package com.project.DAO;

import com.project.entity.Book;
import com.project.entity.ReaderPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReaderPersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReaderPersonDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<ReaderPerson> getAllPerson(){
        return jdbcTemplate.query("SELECT * FROM readerperson",new BeanPropertyRowMapper<>(ReaderPerson.class));
    }

    public ReaderPerson showPerson(int id){
        return jdbcTemplate.query("SELECT * FROM readerperson WHERE id=?",new Object[]{id},new BeanPropertyRowMapper<>(ReaderPerson.class)).
                stream().findAny().orElse(null);
    }

    public void create(ReaderPerson person){
        jdbcTemplate.update("INSERT INTO readerperson (fullName,yearOfBirth) VALUES (?,?)",person.getFullName(),person.getYearOfBirth());
    }

    public void update(int id,ReaderPerson person){
        jdbcTemplate.update("UPDATE readerperson SET fullName=?,yearOfBirth=? WHERE id=?",person.getFullName(),person.getYearOfBirth(),id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM readerperson WHERE id=?",id);
    }

    public ReaderPerson getFullNamePerson(String fullName){
        return jdbcTemplate.query("SELECT * FROM readerperson WHERE fullname=?",new Object[]{fullName},new BeanPropertyRowMapper<>(ReaderPerson.class))
                .stream().findAny().orElse(null);
    }

    public List<Book> getBookByPersonId(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE personId=?",new Object[]{id},new BeanPropertyRowMapper<>(Book.class));
    }
}
