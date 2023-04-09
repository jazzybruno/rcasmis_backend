package com.jazzybruno.example.v1.services;


import com.jazzybruno.example.v1.dto.requests.CreateUserDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateRoleDTO;
import com.jazzybruno.example.v1.dto.requests.UserLoginDTO;
import com.jazzybruno.example.v1.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
    public ResponseEntity<ApiResponse> getAllUsers() throws Exception;
    public ResponseEntity<ApiResponse> getUserById(Long user_id) throws Exception;
    public ResponseEntity<ApiResponse> createUser(CreateUserDTO createUserDTO) throws Exception;
    public ResponseEntity<ApiResponse> updateUser(Long user_id ,  CreateUserDTO createUserDTO) throws Exception;
    ResponseEntity<ApiResponse> deleteUser(Long user_id) throws Exception;
    ResponseEntity<ApiResponse> authenticateUser(UserLoginDTO userLoginDTO) throws Exception;
    ResponseEntity<ApiResponse> updateUserRole(UpdateRoleDTO updateRoleDTO) throws Exception;
     ResponseEntity<ApiResponse> updatePassword( Long user_id , String newPassword) throws Exception;
}
