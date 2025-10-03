package vn.ngocthang.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import vn.ngocthang.entity.Customer;

import java.util.List;

@RestController
@EnableMethodSecurity
public class CustomerController {
    
    final private List<Customer> customers = List.of(
        Customer.builder().id(1L).name("Huỳnh Ngọc Thắng").email("23110327@student.hcmute.edu.vnvn").build(),
        Customer.builder().id(2L).name("Ngọc Thắng").email("23110327@student.hcmute.edu.vnvn").build()
    );
    
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello is Guest");
    }
    
    @GetMapping("/customer/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Customer>> getCustomerList(){
        List<Customer> list = this.customers;
        return ResponseEntity.ok(list);
    }
    
    @GetMapping("/customer/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Customer> getCustomerList(@PathVariable("id") Long id) {
        List<Customer> customers = this.customers.stream().filter(customer ->
            customer.getId().equals(id)).toList();
        return ResponseEntity.ok(customers.get(0));
    }
}
