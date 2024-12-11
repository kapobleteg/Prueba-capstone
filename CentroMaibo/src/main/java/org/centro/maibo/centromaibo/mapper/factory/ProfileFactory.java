package org.centro.maibo.centromaibo.mapper.factory;


import org.centro.maibo.centromaibo.domain.Profile;
import org.centro.maibo.centromaibo.dto.ProfileDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;
import org.centro.maibo.centromaibo.mapper.MapperFactory;
import org.centro.maibo.centromaibo.mapper.mappers.ProfileMapper;
import org.centro.maibo.centromaibo.mapper.mappers.UserMapper;
import org.springframework.stereotype.Service;

@Service

public class ProfileFactory implements MapperFactory<Profile, ProfileDTO> {

    @Override
    public Mapper<Profile, ProfileDTO> createFull() {return new ProfileMapper().withUser(new UserMapper()); }


    @Override
    public Mapper<Profile, ProfileDTO> createSimple()  {return new ProfileMapper(); }

}
