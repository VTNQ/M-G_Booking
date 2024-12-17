package com.vtnq.web.Service;

import com.vtnq.web.DTOs.ContractOwner.ContractOwnerDto;

import java.util.List;

public interface ContractOwnerService {
    public List<ContractOwnerDto> findAll();
    public boolean AcceptRegister(ContractOwnerDto contractOwnerDto);

}
