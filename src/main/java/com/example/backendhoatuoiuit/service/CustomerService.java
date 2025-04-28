package com.example.backendhoatuoiuit.service;

import com.example.backendhoatuoiuit.dto.CustomerDTO;
import com.example.backendhoatuoiuit.dto.LoginResponseDTO;
import com.example.backendhoatuoiuit.dto.SignupRequestDTO;
import com.example.backendhoatuoiuit.entity.Customer;
import com.example.backendhoatuoiuit.exception.CustomException;
import com.example.backendhoatuoiuit.mapper.CustomerMapper;
import com.example.backendhoatuoiuit.repository.CustomerRepository;
import com.example.backendhoatuoiuit.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomerService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customerMapper::toDTO).collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO, String passwordHash) {
        Customer customer = customerMapper.toEntity(customerDTO);
        customer.setPasswordHash(passwordHash);
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        customer.setIsActive(customerDTO.getIsActive());
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    public String loginCustomer(String email, String rawPassword) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Email không tồn tại", 400));

        if (!customer.getIsActive()) {
            throw new CustomException("Tài khoản đang bị khóa", 403);
        }

        if (!passwordEncoder.matches(rawPassword, customer.getPasswordHash())) {
            throw new CustomException("Mật khẩu không đúng", 400);
        }

        return jwtTokenProvider.generateToken(customer.getEmail());
    }

    public CustomerDTO signupCustomer(SignupRequestDTO signupRequest) {
        // Kiểm tra email có tồn tại chưa
        if (customerRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new CustomException("Email đã tồn tại", 400);
        }

        if (customerRepository.findByPhone(signupRequest.getName()).isPresent()) {
            throw  new CustomException("Số điện thoại đã tồn tại", 400);
        }

        Customer customer = new Customer();
        customer.setName(signupRequest.getName());
        customer.setEmail(signupRequest.getEmail());
        customer.setPhone(signupRequest.getPhone());
        customer.setAddress(signupRequest.getAddress());
        customer.setIsActive(true);
        customer.setPasswordHash(passwordEncoder.encode(signupRequest.getPassword()));

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toDTO(savedCustomer);
    }

}
