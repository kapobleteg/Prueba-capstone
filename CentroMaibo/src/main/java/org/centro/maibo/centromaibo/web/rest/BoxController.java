package org.centro.maibo.centromaibo.web.rest;

import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.dto.BoxDTO;
import org.centro.maibo.centromaibo.exception.BoxNotFoundException;
import org.centro.maibo.centromaibo.service.BoxService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("box")
@RequiredArgsConstructor
public class BoxController {

    private final BoxService boxService;


    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public BoxDTO getById(@PathVariable Long id) {
        try {
            return boxService.getById(id);
        } catch (BoxNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<BoxDTO> getAll() {
        return boxService.getAll();
    }

}
