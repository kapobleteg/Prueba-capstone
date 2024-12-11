package org.centro.maibo.centromaibo.mapper.mappers;

import org.centro.maibo.centromaibo.domain.Role;
import org.centro.maibo.centromaibo.domain.User;
import org.centro.maibo.centromaibo.dto.RoleDTO;
import org.centro.maibo.centromaibo.dto.UserDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;

public class UserMapper implements Mapper<User, UserDTO> {

private Mapper<Role, RoleDTO> roleMapper;

public UserMapper withRole(Mapper<Role, RoleDTO> roleMapper) {
    this.roleMapper = roleMapper;
    return this;
}

    @Override
    public UserDTO map(User entity){
    if(entity == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(entity.getId());
        userDTO.setName(entity.getName());
        userDTO.setEmail(entity.getEmail());
        userDTO.setPhone(entity.getPhone());
        userDTO.setStatus(entity.isStatus());
        userDTO.setActivationKey(entity.getActivationKey());
        if(roleMapper != null && entity.getRole() != null) {
            userDTO.setRole(roleMapper.map(entity.getRole()));
        }
        return userDTO;
    }
}
