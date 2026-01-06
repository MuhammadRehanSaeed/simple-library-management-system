package com.rehancode.library_ms.DTO;

import java.time.LocalDate;

import com.rehancode.library_ms.Exceptions.BorrowStatus;

import lombok.Data;

@Data
public class BorrowResponseDTO {
    private int id;
    private int userId;
    private int bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private BorrowStatus status;

}
