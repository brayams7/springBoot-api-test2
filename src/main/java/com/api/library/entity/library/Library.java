package com.api.library.entity.library;

import com.api.library.entity.library.Book;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "library")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id_library", unique = true)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();


    public Library(){

    }

    public Library(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
