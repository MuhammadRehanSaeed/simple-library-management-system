package com.rehancode.library_ms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rehancode.library_ms.Entity.BookModel;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Integer> {

}
