package com.rehancode.library_ms.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rehancode.library_ms.DTO.BookRequestDTO;
import com.rehancode.library_ms.DTO.BookResponseDTO;
import com.rehancode.library_ms.DTO.BorrowRequestDTO;
import com.rehancode.library_ms.DTO.BorrowResponseDTO;
import com.rehancode.library_ms.Entity.BookModel;
import com.rehancode.library_ms.Entity.BorrowRecord;
import com.rehancode.library_ms.Entity.UserModel;
import com.rehancode.library_ms.Exceptions.ApiResponse;
import com.rehancode.library_ms.Exceptions.BorrowStatus;
import com.rehancode.library_ms.Exceptions.UsernameException;
import com.rehancode.library_ms.Repository.BookRepository;
import com.rehancode.library_ms.Repository.BorrowRecordRepository;
import com.rehancode.library_ms.Repository.UserRepository;

@Service
public class BorrowService {
    private BorrowRecordRepository borrowRepository;
    private final UserRepository userRepository;
private final BookRepository bookRepository; // You need this

public BorrowService(BorrowRecordRepository borrowRepository,
                     UserRepository userRepository,
                     BookRepository bookRepository) {
    this.borrowRepository = borrowRepository;
    this.userRepository = userRepository;
    this.bookRepository = bookRepository;
}


        private BorrowResponseDTO convertToDto(BorrowRecord record){
        BorrowResponseDTO response=new BorrowResponseDTO();
        response.setId(record.getId());
        response.setUserId(record.getUser().getId());
        response.setBookId(record.getBook().getId());
        response.setBorrowDate(record.getBorrowDate());
        response.setDueDate(record.getDueDate());
        response.setReturnDate(record.getReturnDate());
        response.setStatus(record.getStatus());
        return response;
    }
    private BorrowRecord convertToEntity(BorrowRequestDTO request){
        UserModel user=userRepository.findById(request.getUserId()).orElseThrow(()->new UsernameException("User not found"));
        BookModel book=bookRepository.findById(request.getBookId()).orElseThrow(()->new UsernameException("Book not found"));       
        BorrowRecord record=new BorrowRecord();
        record.setUser(user);
        record.setBook(book);
        record.setBorrowDate(request.getBorrowDate());
        record.setDueDate(request.getDueDate());
        record.setReturnDate(request.getReturnDate());
        record.setStatus(BorrowStatus.BORROWED);
        return record;
    }
    public ApiResponse<BorrowResponseDTO> createBorrowRecord(BorrowRequestDTO request){
        LocalDate today=LocalDate.now();
        if(request.getBorrowDate().isAfter(today)){
            throw new UsernameException("Borrow date cannot be in the future");
        }
        
        BorrowRecord record=convertToEntity(request);
        BorrowRecord savedRecord=borrowRepository.save(record);
        BorrowResponseDTO response=convertToDto(savedRecord);
       return new ApiResponse<>(201, true, "Borrow record created successfully", response);
    }
     public ApiResponse<BorrowResponseDTO> getBorrowRecordById(int id){
        BorrowRecord record=borrowRepository.findById(id)
        .orElseThrow(()->new UsernameException("Borrow record with id "+id+" not found"));
        BorrowResponseDTO response=convertToDto(record);
        return new ApiResponse<BorrowResponseDTO>(200, true, "Borrow record fetched successfully", response);
    }
    public ApiResponse<BorrowResponseDTO> getBorrowRecordByIdEntity(int id){
    BorrowRecord record = borrowRepository.findById(id)
        .orElseThrow(() -> new UsernameException("Borrow record with id " + id + " not found"));

        LocalDate today = LocalDate.now();
    // Update status in DB if overdue
      if (record.getReturnDate() != null && !record.getReturnDate().isAfter(today)) {
        record.setStatus(BorrowStatus.RETURNED);
    } else if (record.getStatus() == BorrowStatus.BORROWED && today.isAfter(record.getDueDate())) {
        record.setStatus(BorrowStatus.OVERDUE);
    } else {
        record.setStatus(BorrowStatus.BORROWED);
    }

    BorrowResponseDTO response = convertToDto(record);
    return new ApiResponse<>(200, true, "Borrow record fetched successfully", response);
}

// Fetch all borrow records
public ApiResponse<List<BorrowResponseDTO>> getAllBorrowRecords() {
    List<BorrowRecord> records = borrowRepository.findAll();
    List<BorrowResponseDTO> dtos = records.stream()
            .map(this::convertToDto)
            .toList();
    return new ApiResponse<>(200, true, "All borrow records fetched successfully", dtos);
}

// Update a borrow record
public ApiResponse<BorrowResponseDTO> updateBorrowRecord(int id, BorrowRequestDTO request) {
    BorrowRecord record = borrowRepository.findById(id)
            .orElseThrow(() -> new UsernameException("Borrow record with id " + id + " not found"));
    
    record.setUser(userRepository.findById(request.getUserId())
            .orElseThrow(() -> new UsernameException("User not found")));
    record.setBook(bookRepository.findById(request.getBookId())
            .orElseThrow(() -> new UsernameException("Book not found")));
    record.setBorrowDate(request.getBorrowDate());
    record.setDueDate(request.getDueDate());
    record.setReturnDate(request.getReturnDate());
    record.setStatus(request.getStatus());

    borrowRepository.save(record);
    return new ApiResponse<>(200, true, "Borrow record updated successfully", convertToDto(record));
}

// Delete a borrow record
public ApiResponse<String> deleteBorrowRecord(int id) {
    borrowRepository.deleteById(id);
    return new ApiResponse<>(200, true, "Borrow record deleted successfully", "Deleted");
}





}    