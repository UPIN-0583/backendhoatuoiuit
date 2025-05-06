package com.example.backendhoatuoiuit.service;

import com.example.backendhoatuoiuit.dto.CustomerDTO;
import com.example.backendhoatuoiuit.dto.LoginResponseDTO;
import com.example.backendhoatuoiuit.dto.SignupRequestDTO;
import com.example.backendhoatuoiuit.entity.Customer;
import com.example.backendhoatuoiuit.exception.CustomException;
import com.example.backendhoatuoiuit.mapper.CustomerMapper;
import com.example.backendhoatuoiuit.repository.CustomerRepository;
import com.example.backendhoatuoiuit.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public List<CustomerDTO> getAllCustomers() {
        logger.info("Fetching all customers");
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customerMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public CustomerDTO getCustomerById(Integer id) {
        logger.info("Fetching customer with id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomException("Khách hàng không tồn tại", 404));
        return customerMapper.toDTO(customer);
    }

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        logger.info("Creating new customer with email: {}", customerDTO.getEmail());
        if (customerRepository.findByEmail(customerDTO.getEmail()).isPresent()) {
            throw new CustomException("Email đã tồn tại", 400);
        }
        if (customerRepository.findByPhone(customerDTO.getPhone()).isPresent()) {
            throw new CustomException("Số điện thoại đã tồn tại", 400);
        }
        Customer customer = customerMapper.toEntity(customerDTO);

        customer.setIsActive(true); // Mặc định active
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    @Transactional
    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO) {
        logger.info("Updating customer with id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomException("Khách hàng không tồn tại", 404));
        // Kiểm tra email và phone không trùng với khách hàng khác
        customerRepository.findByEmail(customerDTO.getEmail())
                .ifPresent(c -> {
                    if (!c.getId().equals(id)) {
                        throw new CustomException("Email đã tồn tại", 400);
                    }
                });
        customerRepository.findByPhone(customerDTO.getPhone())
                .ifPresent(c -> {
                    if (!c.getId().equals(id)) {
                        throw new CustomException("Số điện thoại đã tồn tại", 400);
                    }
                });
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        customer.setIsActive(customerDTO.getIsActive());
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    @Transactional
    public void deleteCustomer(Integer id) {
        logger.info("Deleting customer with id: {}", id);
        if (!customerRepository.existsById(id)) {
            throw new CustomException("Khách hàng không tồn tại", 404);
        }
        customerRepository.deleteById(id);
    }

    public LoginResponseDTO loginCustomer(String email, String rawPassword) {
        logger.info("Attempting login for email: {}", email);

        // 1. Tìm khách hàng
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Thông tin đăng nhập không đúng", 400));

        // 2. Kiểm tra trạng thái hoạt động
        if (!customer.getIsActive()) {
            throw new CustomException("Tài khoản đang bị khóa", 403);
        }

        // 3. Kiểm tra mật khẩu
        if (!passwordEncoder.matches(rawPassword, customer.getPasswordHash())) {
            throw new CustomException("Thông tin đăng nhập không đúng", 400);
        }

        // 4. Tạo JWT token
        String token = jwtTokenProvider.generateToken(customer.getEmail(), customer.getRole());

        // 5. (Tuỳ chọn) Thiết lập SecurityContext nếu dùng Spring Security context
        String emaill = tokenProvider.getEmailFromToken(token);
        String role = tokenProvider.getRoleFromToken(token);

        UserDetails userDetails = User.builder()
                .username(emaill)
                .password("") // không cần password
                .roles(role)  // dùng role từ token
                .build();


        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.info("Login successful for email: {}", email);

        // 6. Trả về thông tin đăng nhập
        return new LoginResponseDTO(token, customer.getId(), customer.getName(), customer.getEmail(), customer.getRole());

    }


    @Transactional
    public CustomerDTO signupCustomer(SignupRequestDTO signupRequest) {
        logger.info("Signing up new customer with email: {}", signupRequest.getEmail());
        // Kiểm tra email và phone
        if (customerRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new CustomException("Email đã tồn tại", 400);
        }
//        if (customerRepository.findByPhone(signupRequest.getPhone()).isPresent()) {
//            throw new CustomException("Số điện thoại đã tồn tại", 400);
//        }

        // Kiểm tra định dạng email (cơ bản)
        if (!signupRequest.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new CustomException("Email không hợp lệ", 400);
        }

        // Kiểm tra độ dài mật khẩu (ví dụ: tối thiểu 8 ký tự)
        if (signupRequest.getPassword().length() < 8) {
            throw new CustomException("Mật khẩu phải có ít nhất 8 ký tự", 400);
        }

        Customer customer = new Customer();
        customer.setName(signupRequest.getName());
        customer.setEmail(signupRequest.getEmail());
        customer.setPhone(signupRequest.getPhone());
        customer.setAddress(signupRequest.getAddress());
        customer.setIsActive(true);
        customer.setPasswordHash(passwordEncoder.encode(signupRequest.getPassword()));

        String role = signupRequest.getRole() != null ? signupRequest.getRole() : "USER";
        customer.setRole(role);

        Customer savedCustomer = customerRepository.save(customer);
        logger.info("Customer signed up successfully with email: {}", signupRequest.getEmail());
        return customerMapper.toDTO(savedCustomer);
    }
}