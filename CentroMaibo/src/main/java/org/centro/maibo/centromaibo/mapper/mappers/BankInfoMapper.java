package org.centro.maibo.centromaibo.mapper.mappers;

import org.centro.maibo.centromaibo.domain.BankInfo;
import org.centro.maibo.centromaibo.domain.User;
import org.centro.maibo.centromaibo.dto.BankInfoDTO;
import org.centro.maibo.centromaibo.dto.UserDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;

public class BankInfoMapper implements Mapper<BankInfo, BankInfoDTO> {

    private Mapper<User, UserDTO> userUserMapper;

    public BankInfoMapper withUser(Mapper<User, UserDTO> userUserMapper) {
        this.userUserMapper = userUserMapper;
        return this;
    }

    @Override
    public BankInfoDTO map(BankInfo entity) {
        BankInfoDTO dto = new BankInfoDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setBank(entity.getBank());
        dto.setRut(entity.getRut());
        dto.setAccountType(entity.getAccountType());
        dto.setAccountNumber(entity.getAccountNumber());
        dto.setAmountTransfer(entity.getAmountTransfer());
        dto.setReasonTransfer(entity.getReasonTransfer());
        if (entity.getUser() != null && userUserMapper != null) {
            dto.setUser(userUserMapper.map(entity.getUser()));
        }
        return dto;
    }


}
