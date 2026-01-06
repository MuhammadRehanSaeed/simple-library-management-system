package com.rehancode.library_ms.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable=false)
    private String title;
    @Column(nullable=false)
    private String author;
    @Column(nullable=false, unique=true)
    private String isbn;
    @Column(nullable=false)
    private int totalCopies;
    @Column(nullable=false)
    private int availableCopies;

@OneToMany(mappedBy = "book")
private List<BorrowRecord> borrowRecords;


}
