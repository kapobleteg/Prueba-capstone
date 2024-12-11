package org.centro.maibo.centromaibo.mapper.mappers;

import org.centro.maibo.centromaibo.domain.Box;
import org.centro.maibo.centromaibo.dto.BoxDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;

public class BoxMapper implements Mapper<Box, BoxDTO> {

    @Override
    public BoxDTO map(Box entity) {
        BoxDTO boxDTO = new BoxDTO();
        boxDTO.setId(entity.getId());
        boxDTO.setName(entity.getName());
        boxDTO.setAvailability(entity.getAvailability());
        boxDTO.setCapacity(entity.getCapacity());
        boxDTO.setImageURLS(entity.getImageUrls());
        boxDTO.setPrice(entity.getPrice());
        boxDTO.setTime(entity.getTime());
        boxDTO.setLocation(entity.getLocation());
        return boxDTO;
    }
}
