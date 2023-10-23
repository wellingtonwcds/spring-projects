package com.construo.ch.fruitsvegetables.service;

import com.construo.ch.fruitsvegetables.domain.*;
import com.construo.ch.fruitsvegetables.repository.TransactionRepository;
import com.construo.ch.fruitsvegetables.util.PaginationHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @InjectMocks
    private PaginationHelper paginationHelper;

    @Mock
    private ProductService productService;

    @Mock
    private TransactionRepository transactionRepository;

    private Merchant merchant;

    private Customer customer;

    private List<Product> products;

    private Transaction transaction;

    private TransactionDetail transactionDetail;

    @Test
    void findAll() {
        // given
        int pageIndex = 0;
        int pageSize = 10;
        PageRequest pageRequest = paginationHelper.createPageRequest(List.of(), pageIndex, pageSize, "id");
        Page<Transaction> page = paginationHelper.createPage(List.of(), pageIndex, pageSize );

        // when
        Mockito.when(transactionRepository.findAll(pageRequest)).thenReturn(page);
        Page<Transaction> result = transactionService.findAll(pageIndex, pageSize);

        // then
        Assertions.assertEquals(page, result);
    }

    @Test
    void getById() {
        // given
        Long id = 23L;

        // when
        Mockito.when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));
        Transaction transactionResult = transactionService.getById(id);

        // then
        Assertions.assertEquals(transaction, transactionResult);
    }

    @Test
    void save() {
        // given
        Transaction transactionExpected = new Transaction();
        transactionExpected.setId(1L);
        transactionExpected.setCustomer(customer);
        transactionExpected.setTotalAmount(BigDecimal.valueOf(19.0));
        transactionExpected.setTransactionDate(LocalDate.of(2020,1,1));
        transactionExpected.setTransactionTime(LocalTime.of(1,1,1));
        transactionExpected.setTransactionDetails(List.of(transactionDetail));

        // when
        Mockito.when(productService.getById(products.get(0).getId())).thenReturn(products.get(0));
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transactionExpected);
        Transaction transactionResult = transactionService.save(transaction);

        // then
        Assertions.assertEquals(transactionExpected, transactionResult);
    }

    @Test
    void deleteById() {
        // given
        Long transactionId = 23L;

        // when
        transactionService.deleteById(transactionId);

        // then
        Mockito.verify(transactionRepository, Mockito.times(1)).deleteById(transactionId);
    }

    @Test
    void update() {
        // given
        Long transactionId = 23L;
        Transaction transactionUpdated = new Transaction();
        transactionUpdated.setId(24L);
        customer.setId(34L);
        transactionUpdated.setCustomer(customer);
        transactionUpdated.setTotalAmount(BigDecimal.valueOf(29.0));
        transactionUpdated.setTransactionDate(LocalDate.of(2021,2,2));
        transactionUpdated.setTransactionTime(LocalTime.of(2,2,2));
        transactionUpdated.setTransactionDetails(new ArrayList<>());
        transactionUpdated.getTransactionDetails().add(transactionDetail);

        // when
        Mockito.when(productService.getById(products.get(0).getId())).thenReturn(products.get(0));
        Mockito.when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(transactionUpdated);
        Transaction transactionResult = transactionService.update(transactionId, transactionUpdated);

        // then
        Assertions.assertEquals(transactionUpdated, transactionResult);
    }

    @BeforeEach
    void prepare(){
        merchant = new Merchant();
        merchant.setId(1L);

        customer = new Customer();
        customer.setId(1L);

        var product1 = new Product();
        product1.setId(1L);
        product1.setPrice(BigDecimal.valueOf(1.99));
        var product2 = new Product();
        product2.setId(2L);
        product2.setPrice(BigDecimal.valueOf(3.99));
        var product3 = new Product();
        product3.setId(3L);
        product3.setPrice(BigDecimal.valueOf(4.99));

        products = List.of(product1, product2, product3 );

        transaction = new Transaction();
        transaction.setTotalAmount(BigDecimal.valueOf(10.0));
        transaction.setTransactionDate(LocalDate.of(2020,1,1));
        transaction.setTransactionTime(LocalTime.of(1,1,1));
        transaction.setCustomer(customer);

        transactionDetail = new TransactionDetail();
        transactionDetail.setTransaction(transaction);
        transactionDetail.setProduct(products.get(0));
        transactionDetail.setQuantity(1L);
        transactionDetail.setSubTotal(BigDecimal.valueOf(10.0));
        transactionDetail.setId(1L);
        transaction.setTransactionDetails(new ArrayList<>());
        transaction.getTransactionDetails().add(transactionDetail);
    }
}
