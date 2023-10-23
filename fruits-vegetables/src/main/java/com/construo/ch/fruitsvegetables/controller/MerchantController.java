package com.construo.ch.fruitsvegetables.controller;

import com.construo.ch.fruitsvegetables.dto.MerchantDTO;
import com.construo.ch.fruitsvegetables.mapper.MerchantMapper;
import com.construo.ch.fruitsvegetables.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/merchant")
public class MerchantController {

    private final MerchantService merchantService;
    private final MerchantMapper merchantMapper;

    @GetMapping()
    public ResponseEntity<List<MerchantDTO>> getAll(@RequestParam(defaultValue = "0")  int page,
                                                    @RequestParam(defaultValue = "10")int size){
        return ResponseEntity.ok(merchantMapper.toListDTO(merchantService.findAll(page, size).stream().toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchantDTO> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(merchantMapper.toDTO(merchantService.getById(id)));
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody @Valid MerchantDTO merchantDTO){
        return ResponseEntity.ok(merchantMapper.toDTO(merchantService.save(merchantMapper.toEntity(merchantDTO))));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @Min(value = 1) Long id){
        merchantService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchantDTO> update(@Min(value = 1) @PathVariable("id") Long id,
                                              @Valid @RequestBody MerchantDTO merchantDTO){
        return ResponseEntity.ok(merchantMapper.toDTO(merchantService.update(id, merchantMapper.toEntity(merchantDTO))));
    }
}
