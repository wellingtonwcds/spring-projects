package com.construo.ch.fruitsvegetables.service;

import com.construo.ch.fruitsvegetables.domain.Merchant;
import com.construo.ch.fruitsvegetables.repository.MerchantRepository;
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
public class MerchantServiceTest {

    @InjectMocks
    private MerchantService merchantService;

    @InjectMocks
    private PaginationHelper paginationHelper;

    @Mock
    private MerchantRepository merchantRepository;

    @Test
    void findAll() {
        // given
        int pageIndex = 0;
        int pageSize = 10;
        PageRequest pageRequest = paginationHelper.createPageRequest(List.of(), pageIndex, pageSize, "id");
        Page<Merchant> page = paginationHelper.createPage(List.of(), pageIndex, pageSize );

        // when
        Mockito.when(merchantRepository.findAll(pageRequest)).thenReturn(page);
        Page<Merchant> result = merchantService.findAll(pageIndex, pageSize);

        // then
        Assertions.assertEquals(page, result);
    }

    @Test
    void getById() {
        // given
        Long id = 23L;
        Merchant merchantExpected = new Merchant();
        merchantExpected.setId(23L);
        merchantExpected.setName("Merchant");
        merchantExpected.setContactInfo("Contact info");

        // when
        Mockito.when(merchantRepository.findById(id)).thenReturn(Optional.of(merchantExpected));
        Merchant merchantResult = merchantService.getById(id);

        // then
        Assertions.assertEquals(merchantExpected, merchantResult);
    }

    @Test
    void save() {
        // given
        Merchant merchant = new Merchant();
        merchant.setName("Merchant");
        merchant.setContactInfo("Contact info");

        Merchant merchantExpected = new Merchant();
        merchant.setId(24L);
        merchant.setName("Merchant");
        merchant.setContactInfo("Contact info");

        // when
        Mockito.when(merchantRepository.save(merchant)).thenReturn(merchantExpected);
        Merchant merchantResult = merchantService.save(merchant);

        // then
        Assertions.assertEquals(merchantExpected, merchantResult);
    }

    @Test
    void deleteById() {
        // given
        Long merchantId = 23L;

        // when
        merchantService.deleteById(merchantId);

        // then
        Mockito.verify(merchantRepository, Mockito.times(1)).deleteById(merchantId);
    }

    @Test
    void update() {
        // given
        Long merchantId = 23L;
        Merchant merchant = new Merchant();
        merchant.setId(24L);
        merchant.setName("Merchant");
        merchant.setContactInfo("Contact info");

        Merchant merchantUpdated = new Merchant();
        merchantUpdated.setId(24L);
        merchantUpdated.setName("Supplicer updated");
        merchantUpdated.setContactInfo("Contact info");

        // when
        Mockito.when(merchantRepository.findById(merchantId)).thenReturn(Optional.of(merchant));
        Mockito.when(merchantRepository.save(merchantUpdated)).thenReturn(merchantUpdated);
        Merchant merchantResult = merchantService.update(merchantId, merchantUpdated);

        // then
        Assertions.assertEquals(merchantUpdated, merchantResult);
    }

}
