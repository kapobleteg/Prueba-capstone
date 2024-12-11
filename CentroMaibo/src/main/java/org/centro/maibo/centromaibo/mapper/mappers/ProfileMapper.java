package org.centro.maibo.centromaibo.mapper.mappers;

import org.centro.maibo.centromaibo.domain.Profile;
import org.centro.maibo.centromaibo.domain.User;
import org.centro.maibo.centromaibo.dto.ProfileDTO;
import org.centro.maibo.centromaibo.dto.UserDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;

public class ProfileMapper implements Mapper<Profile, ProfileDTO> {
    private Mapper<User, UserDTO> userUserMapper;

    public ProfileMapper withUser(Mapper<User, UserDTO> userUserMapper) {
        this.userUserMapper = userUserMapper;
        return this;
    }
    @Override
    public ProfileDTO map(Profile entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setSpecialty(entity.getSpecialty());
        dto.setCertifications(entity.getCertifications());

        if(userUserMapper != null && entity.getUser() != null) {
            dto.setUser(userUserMapper.map(entity.getUser()));
        }
        return dto;




    }
}
