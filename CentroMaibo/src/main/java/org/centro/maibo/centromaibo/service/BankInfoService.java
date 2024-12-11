package org.centro.maibo.centromaibo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.domain.BankInfo;
import org.centro.maibo.centromaibo.dto.BankInfoDTO;
import org.centro.maibo.centromaibo.exception.BankInfoNotFoundException;
import org.centro.maibo.centromaibo.mapper.Mapper;
import org.centro.maibo.centromaibo.mapper.MapperFactory;
import org.centro.maibo.centromaibo.repository.BankInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankInfoService {
    private final BankInfoRepository bankInfoRepository;
    private final MapperFactory<BankInfo, BankInfoDTO> bankInfoMapperFactory;

    @Transactional
    public BankInfoDTO save(BankInfoDTO dto) {
        BankInfo bankInfo = new BankInfo();
        bankInfo.setId(dto.getId());
        bankInfo.setName(dto.getName());
        bankInfo.setAccountType(dto.getAccountType());
        bankInfo.setAccountNumber(dto.getAccountNumber());
        bankInfo.setAmountTransfer(dto.getAmountTransfer());
        bankInfo.setReasonTransfer(dto.getReasonTransfer());
        Mapper<BankInfo, BankInfoDTO> mapper = bankInfoMapperFactory.createFull();
        return mapper.map(bankInfoRepository.save(bankInfo));
    }

    @Transactional
    public BankInfoDTO update(BankInfoDTO dto) {
        BankInfo bankInfo = bankInfoRepository.findById(dto.getId()).orElseThrow();
        bankInfo.setAmountTransfer(dto.getAmountTransfer());
        bankInfo.setReasonTransfer(dto.getReasonTransfer());
        bankInfo.setAccountNumber(dto.getAccountNumber());
        bankInfo.setAccountType(dto.getAccountType());
        bankInfo.setName(dto.getName());
        Mapper<BankInfo, BankInfoDTO> mapper = bankInfoMapperFactory.createFull();
        return mapper.map(bankInfoRepository.save(bankInfo));
    }

    @Transactional
    public List<BankInfoDTO> getAll() {
        Mapper<BankInfo, BankInfoDTO> mapper = bankInfoMapperFactory.createFull();
        return bankInfoRepository.findAll().stream().map(mapper::map).toList();
    }

    @Transactional
    public BankInfoDTO getById(Long id) {
        Mapper<BankInfo, BankInfoDTO> mapper = bankInfoMapperFactory.createFull();
        BankInfo bankInfo = bankInfoRepository.findByUserId(id).orElseThrow(() ->
                new BankInfoNotFoundException("La informacion del banco no se encuentra en el sistema"));
        return mapper.map(bankInfo);
    }
}
