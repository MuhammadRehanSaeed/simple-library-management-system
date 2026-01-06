package com.rehancode.library_ms.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rehancode.library_ms.DTO.UserRequestDTO;
import com.rehancode.library_ms.DTO.UserResponseDTO;
import com.rehancode.library_ms.Entity.UserModel;
import com.rehancode.library_ms.Exceptions.ApiResponse;
import com.rehancode.library_ms.Exceptions.UsernameException;
import com.rehancode.library_ms.Repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    private UserResponseDTO convertToDto(UserModel user){
        UserResponseDTO response=new UserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        return response;
    }
    private UserModel convertToEntity(UserRequestDTO request){
        UserModel user=new UserModel();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    public ApiResponse<UserResponseDTO> createUser(UserRequestDTO request){
        if(request.getName()==null || request.getEmail()==null || request.getPassword()==null){
            throw new UsernameException("Name, Email and Password cannot be null");
        }
        if(userRepository.existsByName(request.getName())){
            throw new UsernameException("Username already exists");
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new UsernameException("Email already exists");
        }
        UserModel user=convertToEntity(request);
        UserModel savedUser=userRepository.save(user);
        UserResponseDTO response= convertToDto(savedUser);
        return new ApiResponse<UserResponseDTO>(201, true, "User Created Successfully", response);
    }
    public ApiResponse<UserResponseDTO> getUserByEmail(String email){
        UserModel user=userRepository.findByEmail(email)
        .orElseThrow(()->new UsernameException("User with email "+email+" not found"));
        UserResponseDTO response=convertToDto(user);
        return new ApiResponse<UserResponseDTO>(200, true, "User fetched successfully", response);
    }
    public ApiResponse<UserResponseDTO> getUserByName(String name){
        UserModel user=userRepository.findByName(name)
        .orElseThrow(()->new UsernameException("User with name "+name+" not found"));
        UserResponseDTO response=convertToDto(user);
        return new ApiResponse<UserResponseDTO>(200, true, "User fetched successfully", response);
    }
    public ApiResponse<UserResponseDTO> getUserById(int id){
        UserModel user=userRepository.findById(id)
        .orElseThrow(()->new UsernameException("User with id "+id+" not found"));
        UserResponseDTO response=convertToDto(user);
        return new ApiResponse<UserResponseDTO>(200, true, "User fetched successfully", response);
    }
    public ApiResponse<String> deleteUserById(int id){
        UserModel user=userRepository.findById(id)
        .orElseThrow(()->new UsernameException("User with id "+id+" not found"));
        userRepository.deleteById(id);
        return new ApiResponse<String>(200, true, "User deleted successfully", "User with id "+id+" deleted successfully");

    }
    public ApiResponse<UserResponseDTO> updateUser(int id, UserRequestDTO request){
        UserModel user=userRepository.findById(id)
        .orElseThrow(()->new UsernameException("User with id "+id+" not found"));
        if(request.getName()!=null){
            if(userRepository.existsByName(request.getName()) && !user.getName().equals(request.getName())){
                throw new UsernameException("Username already exists");
            }
            user.setName(request.getName());
        }
        if(request.getEmail()!=null){
            if(userRepository.existsByEmail(request.getEmail()) && !user.getEmail().equals(request.getEmail())){
                throw new UsernameException("Email already exists");
            }
            user.setEmail(request.getEmail());
        }
        if(request.getPassword()!=null){
            user.setPassword(request.getPassword());
        }
        UserModel updatedUser=userRepository.save(user);
        UserResponseDTO response=convertToDto(updatedUser);
        return new ApiResponse<UserResponseDTO>(200, true, "User updated successfully", response);
    }

    public ApiResponse<List<UserResponseDTO>> getAllUsers(){
        List<UserModel> users=userRepository.findAll();
        List<UserResponseDTO> responses=users.stream().map(this::convertToDto).collect(Collectors.toList());
        return new ApiResponse<List<UserResponseDTO>>(200, true, "Users fetched successfully", responses);
    }
    public ApiResponse<Page<UserResponseDTO>> getUsersWithPagination(int pageNumber, int pageSize){
        Pageable pageable=PageRequest.of(pageNumber, pageSize);
        Page<UserModel> userPage=userRepository.findAll(pageable);
        Page<UserResponseDTO> responsePage=userPage.map(this::convertToDto);
        return new ApiResponse<Page<UserResponseDTO>>(200, true, "Users fetched successfully", responsePage);
    }

    

}
