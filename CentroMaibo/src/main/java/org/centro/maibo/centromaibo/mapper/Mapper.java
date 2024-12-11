package org.centro.maibo.centromaibo.mapper;

public interface Mapper <E,DTO>{
    DTO map(E entity);
}
