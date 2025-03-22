package org.group5.coolcafe.converter;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.request.AccountCreationRequest;
import org.group5.coolcafe.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountConverter {
    private final ModelMapper modelMapper;

    public Account toAccountEntity(AccountCreationRequest request){
        return modelMapper.map(request, Account.class);
    }
}
