package com.daniel.controller;

import com.daniel.model.Customer;
import com.daniel.service.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    record CustomerRequest (
            String name,
            String email,
            Integer age
    ) {}

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping
    public void addCustomer(@RequestBody CustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }

    @DeleteMapping("{id}")
    public void deleteCustomer(@PathVariable("id") Integer id) {
        customerRepository.deleteById(id);
    }

    @PutMapping("{id}")
    public void updateCustomer (
            @PathVariable("id") Integer id,
            @RequestBody CustomerRequest request
    ) {
        Customer customer = customerRepository.findById(id).orElse(null);

        if(customer != null) {
            customer.setName(request.name());
            customer.setEmail(request.email());
            customer.setAge(request.age());
            customerRepository.save(customer);
        }
    }

}
