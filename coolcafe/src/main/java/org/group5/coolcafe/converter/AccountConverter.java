package org.group5.coolcafe.converter;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.manage_account.ManageAccountDTO;
import org.group5.coolcafe.entity.Account;
import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.request.AccountCreationRequest;
import org.group5.coolcafe.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountConverter {

    private final ModelMapper modelMapper;

    public ManageAccountDTO convertAccountToAccountDTO(Account account) {
        ManageAccountDTO manageAccountDTO = modelMapper.map(account, ManageAccountDTO.class);

        if (manageAccountDTO.getRole()!=null){
            manageAccountDTO.setRole(account.getRole().getCode());
        }
        return manageAccountDTO;
    }

    public Account convertAccountDTOToAccount(ManageAccountDTO manageAccountDTO) {
        Account account = modelMapper.map(manageAccountDTO, Account.class);
        account.setPassword("{noop}"+manageAccountDTO.getPassword());
        return account;
    }

    public Account toAccountEntity(AccountCreationRequest request){
        Account account = modelMapper.map(request, Account.class);
        account.setPassword("{noop}"+request.getPassword());
        return account;
    }
}
