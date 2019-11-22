package com.app.kaptalxis.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "The book must have a title")
    @Length(max = 100, message = "It's too long (max size 100)!")
    private String title;

    private String description;

    @NotBlank(message = "The book must have an author")
    @Length(max = 100, message = "It's too long (max size 100)!")
    private String author;

    @NotBlank(message = "The book should have ISBN")
    @Length(max = 100, message = "It's too long (max size 20)!")
    private String isbn;

    @Column(name = "print_year")
    @NotBlank(message = "The book should have a year of publication")
    @Length(max = 4, message = "It's too long (max size 4, like 1991)!")
    private int printYear;

    @Column(name = "read_already")
    private boolean readAlready;

    @Column(name = "img_path")
    private String imgPath;
}
