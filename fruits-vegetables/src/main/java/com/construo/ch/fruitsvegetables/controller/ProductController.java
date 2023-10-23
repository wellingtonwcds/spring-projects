package com.construo.ch.fruitsvegetables.controller;

import com.construo.ch.fruitsvegetables.dto.ProductDTO;
import com.construo.ch.fruitsvegetables.mapper.ProductMapper;
import com.construo.ch.fruitsvegetables.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(productMapper.toListDTO(productService.findAll(page, size).stream().toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productMapper.toDTO(productService.getById(id)));
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody @Valid ProductDTO productDTO){
        return ResponseEntity.ok(productMapper.toDTO(productService.save(productMapper.toEntity(productDTO))));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @Min(value = 1) Long id){
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@Min(value = 1) @PathVariable("id") Long id,
                                             @Valid @RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productMapper.toDTO(productService.update(id, productMapper.toEntity(productDTO))));
    }

}
