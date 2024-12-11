package org.centro.maibo.centromaibo.web.rest;

import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.dto.ProfileDTO;
import org.centro.maibo.centromaibo.exception.ProfileNotFoundException;
import org.centro.maibo.centromaibo.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final ProfileService profileService;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProfileDTO getById(@PathVariable Long id) {
        try {
            return profileService.getById(id);
        } catch (ProfileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
