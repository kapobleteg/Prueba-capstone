package org.centro.maibo.centromaibo.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.domain.Psychologist;
import org.centro.maibo.centromaibo.dto.PsychologistDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;
import org.centro.maibo.centromaibo.mapper.MapperFactory;
import org.centro.maibo.centromaibo.repository.PsychologistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PsychologistService {
    private final PsychologistRepository psychologistRepository;

    private final MapperFactory<Psychologist, PsychologistDTO> psychologistMapperFactory;
    private final Logger log = LoggerFactory.getLogger(PsychologistService.class);


    @Transactional
    public List<PsychologistDTO> getAll() {
        List<Psychologist> psychologists = psychologistRepository.findAll();
        Mapper<Psychologist, PsychologistDTO> mapper = psychologistMapperFactory.createFull();
        return psychologists.stream().map(mapper::map).toList();
    }


}