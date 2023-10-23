package com.tamanna.interviewcalendar.controller;

import com.tamanna.interviewcalendar.controller.dtos.AvailableTimeSlotDTO;
import com.tamanna.interviewcalendar.controller.dtos.AvailableTimeSlotMatchedDTO;
import com.tamanna.interviewcalendar.controller.dtos.AvailableTimeSlotUpdateDTO;
import com.tamanna.interviewcalendar.controller.mapper.AvailableTimeSlotMapper;
import com.tamanna.interviewcalendar.service.AvailableTimeSlotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/available-time-slot")
@Api(value = "Manage Slots and check matches")
public class AvailableTimeSlotController {

    private final AvailableTimeSlotService availableTimeSlotService;
    private final AvailableTimeSlotMapper availableTimeSlotMapper;

    @GetMapping("/user/{user-id}")
    @ApiOperation(value = "Get all AvailableTimeSlotDTO by userId")
    public ResponseEntity<List<AvailableTimeSlotDTO>> getAllByUserId(@PathVariable("user-id") @Min(value = 1, message = "userId should be greater than 0") Long userId) {
        return ResponseEntity.ok(availableTimeSlotMapper.toListDTO(availableTimeSlotService.findAllByUserId(userId)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get one AvailableTimeSlot by id")
    public ResponseEntity<AvailableTimeSlotDTO> getById(@PathVariable("id") @Min(value = 1, message = "availableTimeSlot should be greater than 0") Long id) {
        return ResponseEntity.ok(availableTimeSlotMapper.toDTO(availableTimeSlotService.getById(id)));
    }

    @PostMapping()
    @ApiOperation(value = "Create an AvailableTimeSlot for one User.")
    public ResponseEntity<?> save(@RequestBody @Valid AvailableTimeSlotDTO availableTimeSlotDTO) {
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(availableTimeSlotService.save(availableTimeSlotMapper.toEntity(availableTimeSlotDTO)).getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Delete one AvailableTimeSlot by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @Min(value = 1, message = "availableTimeSlot should be greater than 0") Long id) {
        availableTimeSlotService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update one AvailableTimeSlot")
    public ResponseEntity<AvailableTimeSlotDTO> update(@PathVariable("id") @Min(value = 1, message = "availableTimeSlot should be greater than 0") Long id,
                                                       @RequestBody @Valid AvailableTimeSlotUpdateDTO availableTimeSlotDTO) {
        return ResponseEntity.ok(availableTimeSlotMapper.toDTO(availableTimeSlotService.update(id, availableTimeSlotMapper.toEntity(availableTimeSlotDTO))));
    }


    @GetMapping("/matched/{candidateId}")
    @ApiOperation(value = "Get all the matches between a candidate and one interviewer")
    private ResponseEntity<AvailableTimeSlotMatchedDTO> getAvailableTimeSlotMatched(@PathVariable("candidateId") @Min(value = 1, message = "candidateId should be greater than 0") Long candidateId,
                                                                                    @RequestParam("interviewer-id") @NotEmpty Set<Long> interviewerId) {
        return ResponseEntity.ok(availableTimeSlotService.findAvailableTimeSlotMatchedBy(candidateId, interviewerId));
    }

}
