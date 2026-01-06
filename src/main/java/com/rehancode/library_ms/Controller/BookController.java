package com.rehancode.library_ms.Controller;
import com.rehancode.library_ms.DTO.BookRequestDTO;
import com.rehancode.library_ms.DTO.BookResponseDTO;
import com.rehancode.library_ms.DTO.UserRequestDTO;
import com.rehancode.library_ms.DTO.UserResponseDTO;
import com.rehancode.library_ms.Exceptions.ApiResponse;
import com.rehancode.library_ms.Service.BookService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

         // ================= POST - Create Employee =================
    @PostMapping
    public ResponseEntity<ApiResponse<BookResponseDTO>> createEmployee(
            @Valid @RequestBody BookRequestDTO dto) {
        ApiResponse<BookResponseDTO> response = bookService.createBook(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // ================= GET - Get Employee by ID =================
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponseDTO>> getEmployeeById(@PathVariable int id) {
        ApiResponse<BookResponseDTO> response = bookService.getBookById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // ================= GET - Get All Employees =================
    // @GetMapping
    // public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> getAllEmployees() {
    //     ApiResponse<List<EmployeeResponseDTO>> response = employeeService.getAllEmployees();
    //     return ResponseEntity.status(response.getStatus()).body(response);
    // }

    // ================= PUT - Update Employee =================
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponseDTO>> updateEmployee(
            @PathVariable int id,
            @Valid @RequestBody BookRequestDTO dto) {
        ApiResponse<BookResponseDTO> response = bookService.updateBook(id, dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // ================= DELETE - Delete Employee =================
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable int id) {
        ApiResponse<String> response = bookService.deleteBookById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping
public ResponseEntity<ApiResponse<?>> getEmployees(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size
) {

    if (page != null && size != null) {
        ApiResponse<Page<BookResponseDTO>> response =
                bookService.getBooksWithPagination(page, size);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    ApiResponse<List<BookResponseDTO>> response =
            bookService.getAllBooks();
    return ResponseEntity.status(response.getStatus()).body(response);
}

}
