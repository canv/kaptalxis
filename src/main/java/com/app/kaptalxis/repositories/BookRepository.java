package com.app.kaptalxis.repositories;

import com.app.kaptalxis.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Book findByTitleIgnoreCase(String title);

    Page<Book> findAll(Pageable pageable);

    Page<Book> findByDescriptionContaining(String description, Pageable pageable);
}
