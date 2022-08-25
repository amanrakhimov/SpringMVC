package com.project.DAO;

import com.project.entity.Book;
import com.project.entity.ReaderPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<Book> getAllBook(){
        return jdbcTemplate.query("SELECT * FROM book",new BeanPropertyRowMapper<>(Book.class));
    }

    public Book showBook(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?",new Object[]{id},new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void create(Book book){
        jdbcTemplate.update("INSERT INTO book (name,author,year) VALUES (?,?,?)",book.getName(),book.getAuthor(),book.getYear());
    }

    public void update(int id,Book book){
        jdbcTemplate.update("UPDATE book SET name=?,author=?,year=? WHERE id=?",book.getName(),book.getAuthor(),book.getYear(),id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM book WHERE id=?",id);
    }

    public ReaderPerson getOwner(int id){
        return jdbcTemplate.query("SELECT r.* FROM book JOIN readerperson r ON r.id=book.personid WHERE book.id=?",
                new Object[]{id},new BeanPropertyRowMapper<>(ReaderPerson.class)).stream().findAny().orElse(null);
    }

    public void deletePersonFromBook(int id){
        jdbcTemplate.update("UPDATE book SET personId=NULL WHERE id=?",id);
    }

    public void choosePersonForBook(int id, ReaderPerson person){
        jdbcTemplate.update("UPDATE book SET personId=? WHERE id=?",person.getId(),id);
    }
}
