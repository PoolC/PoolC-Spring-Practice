package com.poolc.springproject.poolcreborn.repository;

import com.poolc.springproject.poolcreborn.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}