package org.centro.maibo.centromaibo.service;


import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.domain.Box;
import org.centro.maibo.centromaibo.domain.User;
import org.centro.maibo.centromaibo.dto.BoxDTO;
import org.centro.maibo.centromaibo.dto.UserDTO;
import org.centro.maibo.centromaibo.exception.BoxNotFoundException;
import org.centro.maibo.centromaibo.exception.UserNotFoundException;
import org.centro.maibo.centromaibo.mapper.Mapper;
import org.centro.maibo.centromaibo.mapper.MapperFactory;
import org.centro.maibo.centromaibo.repository.BoxRepository;
import org.centro.maibo.centromaibo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoxService {
    private final BoxRepository boxRepository;

    private final MapperFactory<Box, BoxDTO> boxMapperFactory;

    public BoxDTO getById(Long id) throws BoxNotFoundException {
        Box box = boxRepository.findById(id).orElseThrow(() -> new BoxNotFoundException(id));
        return boxMapperFactory.createFull().map(box);
    }

    public List<BoxDTO> getAll() {
        List<Box> boxes = boxRepository.findAll();
        Mapper<Box, BoxDTO> mapper = boxMapperFactory.createFull();
        return boxes.stream().map(mapper::map).toList();
    }


}