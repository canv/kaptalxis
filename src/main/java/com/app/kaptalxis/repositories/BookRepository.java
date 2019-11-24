package com.app.kaptalxis.repositories;

import com.app.kaptalxis.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByTitleIgnoreCase(String title);
    Book findByAuthorIgnoreCase(String author);
    Page<Book> findAll(Pageable pageable);
}
