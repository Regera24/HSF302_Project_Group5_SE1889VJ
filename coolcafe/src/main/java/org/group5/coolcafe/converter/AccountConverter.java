package org.group5.coolcafe.converter;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.AccountDTO;
import org.group5.coolcafe.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountConverter {

    private final ModelMapper modelMapper;

    public AccountDTO convertAccountToAccountDTO(Account account) {
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);

        if (accountDTO.getRole()!=null){
            accountDTO.setRole(account.getRole().getCode());
        }
        return accountDTO;
    }

    public Account convertAccountDTOToAccount(AccountDTO accountDTO) {
        return modelMapper.map(accountDTO, Account.class);
    }
}
