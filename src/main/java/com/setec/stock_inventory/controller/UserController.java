package com.setec.stock_inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.setec.stock_inventory.dto.ApiResponse;
import com.setec.stock_inventory.dto.Request.UserRequestDto;
import com.setec.stock_inventory.dto.Response.UserResponseDto;
import com.setec.stock_inventory.entity.User;
import com.setec.stock_inventory.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(
        @Valid 
        @RequestBody 
        UserRequestDto request
    ) {

        UserResponseDto userResponseDto = userService.createUser(request);

        return new ResponseEntity<>(
            ApiResponse.success("User created successfully", userResponseDto),
            HttpStatus.CREATED
        );
    }

    @GetMapping 
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers(){
        
        List<UserResponseDto> userResponseDto = userService.getAllUsers();

        return ResponseEntity.ok(
            ApiResponse.success("Users fetched successfully", userResponseDto)
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable Long id){
        
        UserResponseDto userResponseDto = userService.getUserById(id);

        return ResponseEntity.ok(
            ApiResponse.success("User fetched successfully", userResponseDto)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(
        @PathVariable Long id,
        @Valid 
        @RequestBody 
        UserRequestDto request
    ) {
        UserResponseDto userResponseDto = userService.updateUser(id, request);
        return ResponseEntity.ok(
            ApiResponse.success("User updated successfully", userResponseDto)
        );
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id){
        
        userService.deleteUser(id);

        return ResponseEntity.ok(
            ApiResponse.success("User deleted successfully", null)
        );
    }
}
