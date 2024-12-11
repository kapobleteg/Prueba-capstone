package org.centro.maibo.centromaibo.mapper.mappers;

import org.centro.maibo.centromaibo.domain.Psychologist;
import org.centro.maibo.centromaibo.domain.User;
import org.centro.maibo.centromaibo.dto.PsychologistDTO;
import org.centro.maibo.centromaibo.dto.UserDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;

public class PsychologistMapper implements Mapper<Psychologist, PsychologistDTO> {
    private Mapper<User, UserDTO> userUserMapper;

    public PsychologistMapper withUser(Mapper<User, UserDTO> userUserMapper) {
        this.userUserMapper = userUserMapper;
        return this;
    }

    @Override
    public PsychologistDTO map(Psychologist entity) {
        if (entity == null) return null;
        PsychologistDTO psychologistDTO = new PsychologistDTO();
        psychologistDTO.setId(entity.getId());
        psychologistDTO.setName(entity.getName());
        if(userUserMapper != null && entity.getUser() != null) {
            psychologistDTO.setUser(userUserMapper.map(entity.getUser()));
        }
        return psychologistDTO;
    }
}
