/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Book;

/**
 *
 * @author gamma
 */
public class BookDTO {
    private String title;
    private String author;
    private String username;

    public BookDTO() {
    } 
    
    public BookDTO(Book book) {
        title = book.getTitle();
        author = book.getAuthor();
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    
}
