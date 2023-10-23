package com.construo.ch.fruitsvegetables.service;

import com.construo.ch.fruitsvegetables.domain.Product;
import com.construo.ch.fruitsvegetables.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
        return productRepository.findAll(pageRequest);
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Product was not found with id: %d", id)));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public Product update(Long id, Product entity) {
        Product productPersisted = getById(id);
        productPersisted.setName(entity.getName());
        productPersisted.setCategory(entity.getCategory());
        productPersisted.setPrice(entity.getPrice());
        productPersisted.setMerchant(entity.getMerchant());
        return save(productPersisted);
    }
}