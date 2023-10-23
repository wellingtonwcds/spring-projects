package com.construo.ch.fruitsvegetables.controller;

import com.construo.ch.fruitsvegetables.dto.TransactionDTO;
import com.construo.ch.fruitsvegetables.mapper.TransactionMapper;
import com.construo.ch.fruitsvegetables.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @GetMapping()
    public ResponseEntity<List<TransactionDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(transactionMapper.toListDTO(transactionService.findAll(page, size).stream().toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(transactionMapper.toDTO(transactionService.getById(id)));
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody @Valid TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionMapper.toDTO(transactionService.save(transactionMapper.toEntity(transactionDTO))));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @Min(value = 1) Long id){
        transactionService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> update(@Min(value = 1) @PathVariable("id") Long id,
                                                 @Valid @RequestBody TransactionDTO transactionDTO ){
        return ResponseEntity.ok(transactionMapper.toDTO(transactionService.update(id, transactionMapper.toEntity(transactionDTO))));
    }

}
