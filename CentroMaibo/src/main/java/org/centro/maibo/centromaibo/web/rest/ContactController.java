package org.centro.maibo.centromaibo.web.rest;

import lombok.AllArgsConstructor;
import org.centro.maibo.centromaibo.dto.ContactDTO;
import org.centro.maibo.centromaibo.service.ContactService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("contact")
@AllArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public void sendContactEmail(@RequestBody ContactDTO contactDTO) {
            contactService.sendContactEmail(contactDTO);
    }

}