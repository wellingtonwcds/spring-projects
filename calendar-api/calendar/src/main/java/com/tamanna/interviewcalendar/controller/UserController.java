package com.tamanna.interviewcalendar.controller;

import com.tamanna.interviewcalendar.controller.dtos.UserDTO;
import com.tamanna.interviewcalendar.controller.mapper.UserMapper;
import com.tamanna.interviewcalendar.domain.RoleType;
import com.tamanna.interviewcalendar.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@Api(value = "Create users. Candidates or Interviewers")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @DeleteMapping("/{user-id}")

    @ApiOperation(value = "Delete an user by an id")
    public void deleteUser(@PathVariable("user-id") @NotNull @Min(value = 1, message = "userId should be greater than 0") Long userId) {
        userService.deleteById(userId);
    }

    @ApiOperation(value = "Insert an user")
    @PostMapping()
    public ResponseEntity<?> saveUser(@Valid @RequestBody @ApiParam(value = "The roleType should be INTERVIEWER or CANDIDATE") UserDTO candidate) {
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(userService.save(userMapper.toEntity(candidate)).getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Find all users")
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userMapper.toListDTO(userService.findAll()));
    }

    @ApiOperation(value = "Get a list of all candidates")
    @GetMapping("/candidates")
    public ResponseEntity<List<UserDTO>> findAllCandidates() {
        return ResponseEntity.ok(userMapper.toListDTO(userService.findAllByRoleType(RoleType.CANDIDATE)));
    }

    @ApiOperation(value = "Get a list of all interviewers")
    @GetMapping("/interviewers")
    public ResponseEntity<List<UserDTO>> findAllInterviewers() {
        return ResponseEntity.ok(userMapper.toListDTO(userService.findAllByRoleType(RoleType.INTERVIEWER)));
    }

    @ApiOperation(value = "Get on user by id")
    @GetMapping("/{user-id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("user-id") @Min(value = 1, message = "userId should be greater than 0") Long userId) {
        return ResponseEntity.ok(userMapper.toDTO(userService.getById(userId)));
    }

    @ApiOperation(value = "Update an user")
    @PutMapping("/{user-id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("user-id") @Min(value = 1, message = "userId should be greater than 0") Long userId, @RequestBody @Valid UserDTO user) {
        return ResponseEntity.ok(userMapper.toDTO(userService.update(userId, userMapper.toEntity(user))));
    }
}
