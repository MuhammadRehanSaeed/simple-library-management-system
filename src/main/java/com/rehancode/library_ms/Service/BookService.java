package com.rehancode.library_ms.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rehancode.library_ms.DTO.BookRequestDTO;
import com.rehancode.library_ms.DTO.BookResponseDTO;
import com.rehancode.library_ms.DTO.UserRequestDTO;
import com.rehancode.library_ms.DTO.UserResponseDTO;
import com.rehancode.library_ms.Entity.BookModel;
import com.rehancode.library_ms.Entity.UserModel;
import com.rehancode.library_ms.Exceptions.ApiResponse;
import com.rehancode.library_ms.Exceptions.UsernameException;
import com.rehancode.library_ms.Repository.BookRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

        private BookResponseDTO convertToDto(BookModel book){
        BookResponseDTO response=new BookResponseDTO();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setIsbn(book.getIsbn());
        response.setTotalCopies(book.getTotalCopies());
        response.setAvailableCopies(book.getAvailableCopies());
        return response;
    }
    private BookModel convertToEntity(BookRequestDTO request){
        BookModel book=new BookModel();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setTotalCopies(request.getTotalCopies());
        book.setAvailableCopies(request.getAvailableCopies());
        return book;
    }

        public ApiResponse<BookResponseDTO> createBook(BookRequestDTO request){
        if(request.getTitle()==null || request.getAuthor()==null || request.getIsbn()==null){
            throw new UsernameException("Title, Author and ISBN cannot be null");
        }
        BookModel book=convertToEntity(request);
        BookModel savedBook=bookRepository.save(book);
        BookResponseDTO response= convertToDto(savedBook);
        return new ApiResponse<BookResponseDTO>(201, true, "Book Created Successfully", response);
    }
     public ApiResponse<BookResponseDTO> getBookById(int id){
        BookModel book=bookRepository.findById(id)
        .orElseThrow(()->new UsernameException("Book with id "+id+" not found"));
        BookResponseDTO response=convertToDto(book);
        return new ApiResponse<BookResponseDTO>(200, true, "Book fetched successfully", response);
    }
    public ApiResponse<String> deleteBookById(int id){
        BookModel book=bookRepository.findById(id)
        .orElseThrow(()->new UsernameException("Book with id "+id+" not found"));
        bookRepository.deleteById(id);
        return new ApiResponse<String>(200, true, "Book deleted successfully", "Book with id "+id+" deleted successfully");

    }

     public ApiResponse<BookResponseDTO> updateBook(int id, BookRequestDTO request){
        BookModel book=bookRepository.findById(id)
        .orElseThrow(()->new UsernameException("Book with id "+id+" not found"));
        if(request.getTitle()!=null){
            book.setTitle(request.getTitle());
        }
        if(request.getAuthor()!=null){
            book.setAuthor(request.getAuthor());
        }
        if(request.getIsbn()!=null){
            book.setIsbn(request.getIsbn());
        }
        if(request.getTotalCopies()!=0){
            book.setTotalCopies(request.getTotalCopies());
        }
        if(request.getAvailableCopies()!=0){
            book.setAvailableCopies(request.getAvailableCopies());
        }
        BookModel updatedBook=bookRepository.save(book);
        BookResponseDTO response=convertToDto(updatedBook);
        return new ApiResponse<BookResponseDTO>(200, true, "Book updated successfully", response);
    }

        public ApiResponse<List<BookResponseDTO>> getAllBooks(){
        List<BookModel> books=bookRepository.findAll();
        List<BookResponseDTO> responses=books.stream().map(this::convertToDto).collect(Collectors.toList());
        return new ApiResponse<List<BookResponseDTO>>(200, true, "Books fetched successfully", responses);
    }
    public ApiResponse<Page<BookResponseDTO>> getBooksWithPagination(int pageNumber, int pageSize){
        Pageable pageable=PageRequest.of(pageNumber, pageSize);
        Page<BookModel> bookPage=bookRepository.findAll(pageable);
        Page<BookResponseDTO> responsePage=bookPage.map(this::convertToDto);
        return new ApiResponse<Page<BookResponseDTO>>(200, true, "Books fetched successfully", responsePage);
    }

    public ApiResponse<BookResponseDTO> getBookStatus(int id){
        BookModel book=bookRepository.findById(id)
        .orElseThrow(()->new UsernameException("Book with id "+id+" not found"));
        LocalDate currentDate=LocalDate.now();
        BookResponseDTO response=convertToDto(book);
        return new ApiResponse<BookResponseDTO>(200, true, "Book status fetched successfully", response);
    }
    

    

    

            }
       
