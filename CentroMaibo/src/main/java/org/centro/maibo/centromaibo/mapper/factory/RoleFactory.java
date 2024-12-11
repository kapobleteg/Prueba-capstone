package org.centro.maibo.centromaibo.mapper.factory;

import org.centro.maibo.centromaibo.domain.Role;
import org.centro.maibo.centromaibo.domain.User;
import org.centro.maibo.centromaibo.dto.RoleDTO;
import org.centro.maibo.centromaibo.dto.UserDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;
import org.centro.maibo.centromaibo.mapper.MapperFactory;
import org.centro.maibo.centromaibo.mapper.mappers.RoleMapper;
import org.centro.maibo.centromaibo.mapper.mappers.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleFactory implements MapperFactory<Role, RoleDTO> {

    @Override
    public Mapper<Role, RoleDTO> createFull() {
        return new RoleMapper();
    }

    @Override
    public Mapper<Role, RoleDTO> createSimple() {
        return new RoleMapper();
    }
}
