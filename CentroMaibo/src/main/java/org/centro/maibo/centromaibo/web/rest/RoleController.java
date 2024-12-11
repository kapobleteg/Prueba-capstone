package org.centro.maibo.centromaibo.web.rest;

import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.dto.RoleDTO;
import org.centro.maibo.centromaibo.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController
@RequestMapping("role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO getRoleById(@PathVariable Long id) {
        try {
            return roleService.getRoleById(id);
        } catch (RoleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDTO createRole(@RequestBody RoleDTO roleDTO) {
        return roleService.createRole(roleDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
}
