package org.centro.maibo.centromaibo.web.rest;

import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.dto.BankInfoDTO;
import org.centro.maibo.centromaibo.service.BankInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bank-info")
@RequiredArgsConstructor
public class BankInfoController {
    private final BankInfoService bankInfoService;

    @GetMapping
    public List<BankInfoDTO> getAll(){
         return bankInfoService.getAll();
    }

    @GetMapping("{id}")
    public BankInfoDTO getById(@PathVariable Long id){
        return bankInfoService.getById(id);
    }

    @PostMapping("create")
    public BankInfoDTO create(@RequestBody BankInfoDTO bankInfoDTO){
        return bankInfoService.save(bankInfoDTO);
    }

    @PostMapping("update")
    public BankInfoDTO update(@RequestBody BankInfoDTO bankInfoDTO){
        return bankInfoService.update(bankInfoDTO);
    }

}
