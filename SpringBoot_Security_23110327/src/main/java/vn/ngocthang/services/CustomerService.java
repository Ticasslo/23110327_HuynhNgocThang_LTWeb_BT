package vn.ngocthang.services;

import vn.ngocthang.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    
    List<Customer> findAll();
    
    Optional<Customer> findById(Long id);
    
    Customer save(Customer customer);
    
    void deleteById(Long id);
    
    Optional<Customer> findByEmail(String email);
    
    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
