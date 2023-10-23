package com.construo.ch.fruitsvegetables.service;

import com.construo.ch.fruitsvegetables.domain.Product;
import com.construo.ch.fruitsvegetables.domain.Transaction;
import com.construo.ch.fruitsvegetables.domain.TransactionDetail;
import com.construo.ch.fruitsvegetables.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductService productService;

    public Page<Transaction> findAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
        return transactionRepository.findAll(pageRequest);
    }

    public Transaction getById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Transaction was not found with id: %d", id)));
    }

    @Transactional
    public Transaction save(Transaction transaction){
        syncTransactionTotalSubTotal(transaction.getTransactionDetails(), transaction);
        return transactionRepository.save(transaction);
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Transactional
    public Transaction update(Long id, Transaction entity) {
        Transaction transactionPersisted = getById(id);
        transactionPersisted.setCustomer(entity.getCustomer());
        transactionPersisted.setTransactionDate(entity.getTransactionDate());
        syncTransactionTotalSubTotal(entity.getTransactionDetails(),transactionPersisted);
        transactionPersisted.getTransactionDetails().clear();
        transactionPersisted.getTransactionDetails().addAll(entity.getTransactionDetails());
        return save(transactionPersisted);
    }

    private void syncTransactionTotalSubTotal(List<TransactionDetail> transactionDetailList, Transaction transaction){
        var total = transactionDetailList.stream().map((t) -> {
            t.setTransaction(transaction);
            Product product = productService.getById(t.getProduct().getId());
            t.setSubTotal(product.getPrice().multiply(BigDecimal.valueOf(t.getQuantity())));
            return t.getSubTotal();
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        transaction.setTotalAmount(total);
    }
}
