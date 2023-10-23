package com.construo.ch.fruitsvegetables.service;

import com.construo.ch.fruitsvegetables.domain.Customer;
import com.construo.ch.fruitsvegetables.repository.CustomerRepository;
import com.construo.ch.fruitsvegetables.util.PaginationHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @InjectMocks
    private PaginationHelper paginationHelper;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void findAll() {
        // given
        int pageIndex = 0;
        int pageSize = 10;
        PageRequest pageRequest = paginationHelper.createPageRequest(List.of(), pageIndex, pageSize, "id");
        Page<Customer> page = paginationHelper.createPage(List.of(), pageIndex, pageSize );

        // when
        Mockito.when(customerRepository.findAll(pageRequest)).thenReturn(page);
        Page<Customer> result = customerService.findAll(pageIndex, pageSize);

        // then
        Assertions.assertEquals(page, result);
    }

    @Test
    void getById() {
        // given
        Long id = 23L;
        Customer customerExpected = new Customer();
        customerExpected.setId(23L);
        customerExpected.setFirstName("First Name");
        customerExpected.setLastName("Last Name");

        // when
        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(customerExpected));
        Customer customerResult = customerService.getById(id);

        // then
        Assertions.assertEquals(customerExpected, customerResult);
    }

    @Test
    void save() {
        // given
        Customer customer = new Customer();
        customer.setFirstName("First Name");
        customer.setLastName("Last Name");

        Customer customerExpected = new Customer();
        customer.setId(24L);
        customer.setFirstName("First Name");
        customer.setLastName("Last Name");

        // when
        Mockito.when(customerRepository.save(customer)).thenReturn(customerExpected);
        Customer customerResult = customerService.save(customer);

        // then
        Assertions.assertEquals(customerExpected, customerResult);
    }

    @Test
    void deleteById() {
        // given
        Long customerId = 23L;

        // when
        customerService.deleteById(customerId);

        // then
        Mockito.verify(customerRepository, Mockito.times(1)).deleteById(customerId);
    }

    @Test
    void update() {
        // given
        Long customerId = 23L;
        Customer customer = new Customer();
        customer.setId(24L);
        customer.setFirstName("First Name");
        customer.setLastName("Last Name");

        Customer customerUpdated = new Customer();
        customerUpdated.setId(24L);
        customerUpdated.setFirstName("First Name updated");
        customerUpdated.setLastName("Last Name updated");
        customerUpdated.setContactInfo("Updated");

        // when
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Mockito.when(customerRepository.save(customerUpdated)).thenReturn(customerUpdated);
        Customer customerResult = customerService.update(customerId, customerUpdated);

        // then
        Assertions.assertEquals(customerUpdated, customerResult);
    }
}
