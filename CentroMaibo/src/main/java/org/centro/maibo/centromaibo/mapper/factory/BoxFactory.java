package org.centro.maibo.centromaibo.mapper.factory;

import org.centro.maibo.centromaibo.domain.Box;
import org.centro.maibo.centromaibo.dto.BoxDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;
import org.centro.maibo.centromaibo.mapper.MapperFactory;
import org.centro.maibo.centromaibo.mapper.mappers.BoxMapper;
import org.springframework.stereotype.Service;

@Service
public class BoxFactory implements MapperFactory<Box, BoxDTO> {

    @Override
    public Mapper<Box, BoxDTO> createFull() {
        return new BoxMapper();
    }

    @Override
    public Mapper<Box, BoxDTO> createSimple() {
        return new BoxMapper();
    }
}
