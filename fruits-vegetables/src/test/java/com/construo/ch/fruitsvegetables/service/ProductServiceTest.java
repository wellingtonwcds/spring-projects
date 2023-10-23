package com.construo.ch.fruitsvegetables.service;

import com.construo.ch.fruitsvegetables.domain.Product;
import com.construo.ch.fruitsvegetables.domain.Merchant;
import com.construo.ch.fruitsvegetables.repository.ProductRepository;
import com.construo.ch.fruitsvegetables.util.CategoryEnum;
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
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @InjectMocks
    private PaginationHelper paginationHelper;

    @Mock
    private ProductRepository productRepository;

    private Merchant merchant;

    @BeforeEach
    void beforeEach(){
        merchant = new Merchant();
        merchant.setId(1L);
    }

    @Test
    void findAll() {
        // given
        int pageIndex = 0;
        int pageSize = 10;
        PageRequest pageRequest = paginationHelper.createPageRequest(List.of(), pageIndex, pageSize, "id");
        Page<Product> page = paginationHelper.createPage(List.of(), pageIndex, pageSize );

        // when
        Mockito.when(productRepository.findAll(pageRequest)).thenReturn(page);
        Page<Product> result = productService.findAll(pageIndex, pageSize);

        // then
        Assertions.assertEquals(page, result);
    }

    @Test
    void getById() {
        // given
        Long id = 23L;
        Product productExpected = new Product();
        productExpected.setId(23L);
        productExpected.setName("Name");
        productExpected.setPrice(BigDecimal.valueOf(1.99));
        productExpected.setCategory(CategoryEnum.FRUIT);
        productExpected.setMerchant(merchant);

        // when
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(productExpected));
        Product customerResult = productService.getById(id);

        // then
        Assertions.assertEquals(productExpected, customerResult);
    }

    @Test
    void save() {
        // given
        Product product = new Product();
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(1.99));
        product.setCategory(CategoryEnum.FRUIT);
        product.setMerchant(merchant);

        Product productExpected = new Product();
        productExpected.setId(24L);
        productExpected.setName("Name");
        productExpected.setPrice(BigDecimal.valueOf(1.99));
        productExpected.setCategory(CategoryEnum.FRUIT);
        productExpected.setMerchant(merchant);

        // when
        Mockito.when(productRepository.save(product)).thenReturn(productExpected);
        Product customerResult = productService.save(product);

        // then
        Assertions.assertEquals(productExpected, customerResult);
    }

    @Test
    void deleteById() {
        // given
        Long productId = 23L;

        // when
        productService.deleteById(productId);

        // then
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(productId);
    }

    @Test
    void update() {
        // given
        Long productId = 23L;
        Product product = new Product();
        product.setId(24L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(2.99));
        product.setCategory(CategoryEnum.VEGETABLE);
        product.setMerchant(merchant);

        Product productUpdated = new Product();
        productUpdated.setId(24L);
        productUpdated.setName("Name updated");
        productUpdated.setPrice(BigDecimal.valueOf(3.99));
        productUpdated.setCategory(CategoryEnum.FRUIT);
        merchant.setId(99L);
        productUpdated.setMerchant(merchant);

        // when
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(productUpdated)).thenReturn(productUpdated);
        Product customerResult = productService.update(productId, productUpdated);

        // then
        Assertions.assertEquals(productUpdated, customerResult);
    }

}
