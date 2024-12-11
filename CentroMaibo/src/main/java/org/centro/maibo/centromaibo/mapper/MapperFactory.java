package org.centro.maibo.centromaibo.mapper;

public interface MapperFactory <E, DTO>{
    Mapper<E,DTO> createSimple();

    Mapper<E, DTO> createFull();
}
