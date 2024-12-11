package org.centro.maibo.centromaibo.web.rest;

import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.dto.LoginRequestDTO;
import org.centro.maibo.centromaibo.dto.RegistrationDTO;
import org.centro.maibo.centromaibo.dto.UserDTO;
import org.centro.maibo.centromaibo.exception.AuthenticationException;
import org.centro.maibo.centromaibo.exception.UserNotActivatedException;
import org.centro.maibo.centromaibo.exception.UserNotFoundException;
import org.centro.maibo.centromaibo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getById(@PathVariable Long id) {
        try {
            return userService.getById(id);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAll() {
        return userService.getAll();
    }


    @PostMapping("activate")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO activation(@RequestBody RegistrationDTO dto) {
        try {
            return userService.activateRegistration(dto.getKey(), dto.getPassword());
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PatchMapping("status/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> payload) {
        boolean status = payload.get("status");
        try {
            userService.updateStatus(id, status);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
             userService.updateUser(id, userDTO);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("delete/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
    }
}