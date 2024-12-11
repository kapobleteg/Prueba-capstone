package org.centro.maibo.centromaibo.service;

import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.config.MailService;
import org.centro.maibo.centromaibo.dto.ContactDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final MailService mailService;

    public void sendContactEmail(ContactDTO contactDTO) {
            mailService.sendContactEmail(contactDTO);
    }
}
