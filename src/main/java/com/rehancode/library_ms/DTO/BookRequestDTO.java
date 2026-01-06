package com.rehancode.library_ms.DTO;

import lombok.Data;


@Data
public class BookRequestDTO {
    private String title;
    private String author;
    private String isbn;
    private int totalCopies;
    private int availableCopies;

}
