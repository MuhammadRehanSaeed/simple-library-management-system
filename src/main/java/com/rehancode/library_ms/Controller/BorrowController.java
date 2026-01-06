package com.rehancode.library_ms.Controller;

import org.springframework.web.bind.annotation.*;

import com.rehancode.library_ms.DTO.BorrowRequestDTO;
import com.rehancode.library_ms.DTO.BorrowResponseDTO;
import com.rehancode.library_ms.Exceptions.ApiResponse;
import com.rehancode.library_ms.Service.BorrowService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    // ================= POST - Create Borrow Record =================
    @PostMapping
    public ApiResponse<BorrowResponseDTO> createBorrowRecord(@Valid @RequestBody BorrowRequestDTO request) {
        return borrowService.createBorrowRecord(request);
    }

    // ================= GET - Fetch Borrow Record by ID =================
    @GetMapping("/{id}")
    public ApiResponse<BorrowResponseDTO> getBorrowRecord(@PathVariable int id) {
        // Option 2: fetch and update status if overdue
        return borrowService.getBorrowRecordByIdEntity(id);
    }

    // ================= GET - Fetch All Borrow Records =================
    @GetMapping
    public ApiResponse<List<BorrowResponseDTO>> getAllBorrowRecords() {
        // You can implement a method in BorrowService to fetch all records
        return borrowService.getAllBorrowRecords();
    }

    // ================= PUT - Update Borrow Record (Optional) =================
    @PutMapping("/{id}")
    public ApiResponse<BorrowResponseDTO> updateBorrowRecord(@PathVariable int id, @Valid @RequestBody BorrowRequestDTO request) {
        return borrowService.updateBorrowRecord(id, request);
    }

    // ================= DELETE - Delete Borrow Record =================
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBorrowRecord(@PathVariable int id) {
        return borrowService.deleteBorrowRecord(id);
    }
}
