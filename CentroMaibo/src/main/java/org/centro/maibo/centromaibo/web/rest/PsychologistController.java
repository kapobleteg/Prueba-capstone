package org.centro.maibo.centromaibo.web.rest;

import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.dto.LoginRequestDTO;
import org.centro.maibo.centromaibo.dto.PsychologistDTO;
import org.centro.maibo.centromaibo.dto.UserDTO;
import org.centro.maibo.centromaibo.exception.AuthenticationException;
import org.centro.maibo.centromaibo.exception.UserNotActivatedException;
import org.centro.maibo.centromaibo.exception.UserNotFoundException;
import org.centro.maibo.centromaibo.service.PsychologistService;
import org.centro.maibo.centromaibo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("psychologist")
@RequiredArgsConstructor
public class PsychologistController {
    private final PsychologistService psychologistService;


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<PsychologistDTO> getAll() {
        return psychologistService.getAll();
    }


}