package com.construo.ch.fruitsvegetables.service;

import com.construo.ch.fruitsvegetables.domain.Merchant;
import com.construo.ch.fruitsvegetables.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;
    public Page<Merchant> findAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
        return merchantRepository.findAll(pageRequest);
    }

    public Merchant getById(Long id) {
        return merchantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Merchant was not found with id: %d", id)));
    }

    @Transactional
    public Merchant save(Merchant merchant){
        return merchantRepository.save(merchant);
    }

    public void deleteById(Long id) {
        merchantRepository.deleteById(id);
    }

    @Transactional
    public Merchant update(Long id, Merchant entity){
        Merchant merchantPersisted = getById(id);
        merchantPersisted.setName(entity.getName());
        merchantPersisted.setContactInfo(entity.getContactInfo());
        return save(merchantPersisted);
    }
}
