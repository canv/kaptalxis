package com.app.kaptalxis.repositories;

import com.app.kaptalxis.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Book findByTitleIgnoreCase(String title);
    List<Book> findByAuthorIgnoreCase(String author);
    List<Book> findByDescriptionIgnoreCase(String description);

//    @QueryHints(value = { @QueryHint(name = "name", value = "value")}, forCounting = false)
    Page<Book> findByDescription(String description, Pageable pageable);
    Page<Book> findAll(Pageable pageable);
}
