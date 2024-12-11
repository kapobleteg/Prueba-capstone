package org.centro.maibo.centromaibo.mapper.factory;

import org.centro.maibo.centromaibo.domain.BankInfo;
import org.centro.maibo.centromaibo.dto.BankInfoDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;
import org.centro.maibo.centromaibo.mapper.MapperFactory;
import org.centro.maibo.centromaibo.mapper.mappers.BankInfoMapper;
import org.centro.maibo.centromaibo.mapper.mappers.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class BankInfoFactory implements MapperFactory<BankInfo,BankInfoDTO> {
    @Override
    public Mapper<BankInfo, BankInfoDTO> createSimple() {
        return new BankInfoMapper();
    }

    @Override
    public Mapper<BankInfo, BankInfoDTO> createFull() {
        return new BankInfoMapper().withUser(new UserMapper());
    }
}
