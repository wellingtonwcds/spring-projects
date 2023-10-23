package com.construo.ch.fruitsvegetables.service;

import com.construo.ch.fruitsvegetables.domain.Customer;
import com.construo.ch.fruitsvegetables.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Page<Customer> findAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
        return customerRepository.findAll(pageRequest);
    }

    public Customer getById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Customer was not found with id: %d", id)));
    }

    @Transactional
    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }

    public void deleteById(Long id){
        customerRepository.deleteById(id);
    }

    @Transactional
    public Customer update(Long id, Customer entity) {
        Customer customerPersisted = getById(id);
        customerPersisted.setFirstName(entity.getFirstName());
        customerPersisted.setLastName(entity.getLastName());
        customerPersisted.setContactInfo(entity.getContactInfo());
        return save(customerPersisted);
    }
}
