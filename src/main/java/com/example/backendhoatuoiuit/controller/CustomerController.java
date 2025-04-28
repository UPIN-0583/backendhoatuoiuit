package com.example.backendhoatuoiuit.controller;

import com.example.backendhoatuoiuit.dto.CustomerDTO;
import com.example.backendhoatuoiuit.dto.LoginRequestDTO;
import com.example.backendhoatuoiuit.dto.LoginResponseDTO;
import com.example.backendhoatuoiuit.dto.SignupRequestDTO;
import com.example.backendhoatuoiuit.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Integer id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO, @RequestParam String passwordHash) {
        return customerService.createCustomer(customerDTO, passwordHash);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Integer id, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginRequestDTO loginRequest) {
        String token = customerService.loginCustomer(loginRequest.getEmail(), loginRequest.getPasswordHash());
        return ResponseEntity.ok().body(Map.of("token", token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Client tự xóa token, server chỉ trả thông báo
        return ResponseEntity.ok().body("Đã đăng xuất thành công");
    }

    @PostMapping("/signup")
    public CustomerDTO signupCustomer(@RequestBody SignupRequestDTO signupRequest) {
        return customerService.signupCustomer(signupRequest);
    }


}
