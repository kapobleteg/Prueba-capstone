package org.centro.maibo.centromaibo.mapper.mappers;

import org.centro.maibo.centromaibo.domain.Role;
import org.centro.maibo.centromaibo.domain.User;
import org.centro.maibo.centromaibo.dto.RoleDTO;
import org.centro.maibo.centromaibo.dto.UserDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;

public class RoleMapper implements Mapper<Role, RoleDTO> {

    @Override
    public RoleDTO map(Role entity) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(entity.getId());
        roleDTO.setName(entity.getName());
        roleDTO.setPermissions(entity.getPermissions());
        return roleDTO;
    }
}
