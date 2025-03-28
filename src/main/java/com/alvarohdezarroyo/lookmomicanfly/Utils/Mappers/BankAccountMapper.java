package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.BankAccountDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.BankAccount;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {

    public static BankAccount toBankAccount(BankAccountDTO bankAccountDTO, User user){
        final BankAccount bankAccount=new BankAccount();
        bankAccount.setActive(true);
        bankAccount.setNumber(bankAccount.getNumber());
        bankAccount.setUser(user);
        return bankAccount;
    }

    public static BankAccountDTO toDTO(BankAccount bankAccount){
        if(bankAccount==null)
            return null;
        final BankAccountDTO bankAccountDTO=new BankAccountDTO();
        bankAccountDTO.setId(bankAccount.getId());
        bankAccountDTO.setNumber(bankAccount.getNumber());
        bankAccountDTO.setUserId(bankAccount.getUser().getId());
        return bankAccountDTO;
    }

}
