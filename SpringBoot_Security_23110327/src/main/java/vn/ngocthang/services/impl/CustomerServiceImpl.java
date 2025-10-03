package vn.ngocthang.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ngocthang.entity.Customer;
import vn.ngocthang.repository.CustomerRepository;
import vn.ngocthang.services.CustomerService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    
    private final CustomerRepository customerRepository;
    
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    
    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }
    
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
    
    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
    
    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
    
    @Override
    public Optional<Customer> findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }
}
