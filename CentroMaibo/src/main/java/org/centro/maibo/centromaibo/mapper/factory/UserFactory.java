package org.centro.maibo.centromaibo.mapper.factory;

import org.centro.maibo.centromaibo.domain.User;
import org.centro.maibo.centromaibo.dto.UserDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;
import org.centro.maibo.centromaibo.mapper.MapperFactory;
import org.centro.maibo.centromaibo.mapper.mappers.RoleMapper;
import org.centro.maibo.centromaibo.mapper.mappers.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserFactory implements MapperFactory<User, UserDTO> {

    @Override
    public Mapper<User, UserDTO> createFull() {
        RoleMapper roleMapper = new RoleMapper();
        return new UserMapper().withRole(roleMapper);
    }

    @Override
    public Mapper<User, UserDTO> createSimple() {
        return new UserMapper();
    }
}
