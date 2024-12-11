package org.centro.maibo.centromaibo.mapper.factory;

import org.centro.maibo.centromaibo.domain.Psychologist;
import org.centro.maibo.centromaibo.dto.PsychologistDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;
import org.centro.maibo.centromaibo.mapper.MapperFactory;
import org.centro.maibo.centromaibo.mapper.mappers.PsychologistMapper;
import org.centro.maibo.centromaibo.mapper.mappers.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class PsychologistFactory implements MapperFactory<Psychologist, PsychologistDTO> {

    @Override
    public Mapper<Psychologist, PsychologistDTO> createFull() {
        return new PsychologistMapper().withUser(new UserMapper());
    }

    @Override
    public Mapper<Psychologist, PsychologistDTO> createSimple() {
        return new PsychologistMapper();
    }
}
