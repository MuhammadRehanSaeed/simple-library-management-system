package com.rehancode.library_ms.Controller;

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

import com.rehancode.library_ms.DTO.UserRequestDTO;
import com.rehancode.library_ms.DTO.UserResponseDTO;
import com.rehancode.library_ms.Exceptions.ApiResponse;
import com.rehancode.library_ms.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

        // ================= POST - Create Employee =================
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> createEmployee(
            @Valid @RequestBody UserRequestDTO dto) {
        ApiResponse<UserResponseDTO> response = userService.createUser(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // ================= GET - Get Employee by ID =================
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getEmployeeById(@PathVariable int id) {
        ApiResponse<UserResponseDTO> response = userService.getUserById(id);
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
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateEmployee(
            @PathVariable int id,
            @Valid @RequestBody UserRequestDTO dto) {
        ApiResponse<UserResponseDTO> response = userService.updateUser(id, dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // ================= DELETE - Delete Employee =================
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable int id) {
        ApiResponse<String> response = userService.deleteUserById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getEmployeeByUsername(@PathVariable String username) {
        ApiResponse<UserResponseDTO> response = userService.getUserByName(username);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
public ResponseEntity<ApiResponse<?>> getEmployees(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size
) {

    if (page != null && size != null) {
        ApiResponse<Page<UserResponseDTO>> response =
                userService.getUsersWithPagination(page, size);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    ApiResponse<List<UserResponseDTO>> response =
            userService.getAllUsers();
    return ResponseEntity.status(response.getStatus()).body(response);
}

}
