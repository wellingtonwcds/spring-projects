package com.construo.ch.fruitsvegetables.controller;

import com.construo.ch.fruitsvegetables.dto.CustomerDTO;
import com.construo.ch.fruitsvegetables.mapper.CustomerMapper;
import com.construo.ch.fruitsvegetables.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping()
    public ResponseEntity<List<CustomerDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(customerMapper.toListDTO(customerService.findAll(page, size).stream().toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerMapper.toDTO(customerService.getById(id)));
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody @Valid CustomerDTO CustomerDTO) {
        return ResponseEntity.ok(customerMapper.toDTO(customerService.save(customerMapper.toEntity(CustomerDTO))));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @Min(value = 1) Long id){
        customerService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@Min(value = 1) @PathVariable("id")Long id,
                                              @Valid @RequestBody CustomerDTO CustomerDTO){
        return ResponseEntity.ok(customerMapper.toDTO(customerService.update(id, customerMapper.toEntity(CustomerDTO))));
    }
}
