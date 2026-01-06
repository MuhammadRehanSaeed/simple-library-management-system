package com.rehancode.library_ms.Entity;

import java.time.LocalDate;

import com.rehancode.library_ms.Exceptions.BorrowStatus;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private UserModel user;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "book_id", nullable = false)
private BookModel book;



    @Column(nullable = false)
private LocalDate borrowDate;

@Column(nullable = false)
private LocalDate dueDate;
@Column(nullable = false)
private LocalDate returnDate;

@Enumerated(EnumType.STRING)
@Column(nullable = false)
private BorrowStatus status;

}