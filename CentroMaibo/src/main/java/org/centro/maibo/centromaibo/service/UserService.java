package org.centro.maibo.centromaibo.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.config.MailService;
import org.centro.maibo.centromaibo.config.RandomUtil;
import org.centro.maibo.centromaibo.domain.Role;
import org.centro.maibo.centromaibo.domain.User;
import org.centro.maibo.centromaibo.dto.LoginRequestDTO;
import org.centro.maibo.centromaibo.dto.UserDTO;
import org.centro.maibo.centromaibo.exception.AuthenticationException;
import org.centro.maibo.centromaibo.exception.UserNotActivatedException;
import org.centro.maibo.centromaibo.exception.UserNotFoundException;
import org.centro.maibo.centromaibo.mapper.Mapper;
import org.centro.maibo.centromaibo.mapper.MapperFactory;
import org.centro.maibo.centromaibo.repository.RoleRepository;
import org.centro.maibo.centromaibo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final MapperFactory<User, UserDTO> userMapperFactory;
    private final RoleRepository roleRepository;
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;

    public UserDTO getById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userMapperFactory.createFull().map(user);
    }

    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        Mapper<User, UserDTO> mapper = userMapperFactory.createFull();
        return users.stream().map(mapper::map).toList();
    }


    @Transactional
    public UserDTO activateRegistration(String key, String password) throws AuthenticationException {
        User user = userRepository.findByActivationKey(key);
        user.setActivationKey(null);
        user.setStatus(true);
        user.setPassword(passwordEncoder.encode(password));
        return userMapperFactory.createFull().map(user);
    }


    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhone(userDTO.getPhone());
        user.setStatus(userDTO.isStatus());
        user.setRole(roleRepository.findById(userDTO.getRole().getId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado")));
        user.setActivationKey(RandomUtil.generateActivationKey());
        user = userRepository.save(user);
        try {
            mailService.sendActivationEmail(user);
        } catch (Exception e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
        return userMapperFactory.createFull().map(user);
    }

    @Transactional
    public void updateStatus(Long id, boolean status) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setStatus(status);
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long id, UserDTO userDTO) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setStatus(userDTO.isStatus());

        Role role = roleRepository.findById(userDTO.getRole().getId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        user.setRole(role);

        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
