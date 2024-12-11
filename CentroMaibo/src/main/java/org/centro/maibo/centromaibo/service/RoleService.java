package org.centro.maibo.centromaibo.service;

import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.domain.Role;
import org.centro.maibo.centromaibo.dto.RoleDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;
import org.centro.maibo.centromaibo.mapper.MapperFactory;
import org.centro.maibo.centromaibo.repository.RoleRepository;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final MapperFactory<Role, RoleDTO> roleRoleMapperFactory;

    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        Mapper<Role, RoleDTO> mapper = roleRoleMapperFactory.createFull();
        return roles.stream().map(mapper::map).toList();
    }

    public RoleDTO getRoleById(Long id) throws RoleNotFoundException {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("El role con el #" +
                id + " no se encuentra en el sistema"));
        return roleRoleMapperFactory.createFull().map(role);
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setPermissions(roleDTO.getPermissions());
        Role savedRole = roleRepository.save(role);
        return new RoleDTO(savedRole.getId(), savedRole.getName(), savedRole.getPermissions());
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}