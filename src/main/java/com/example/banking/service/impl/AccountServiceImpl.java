package com.example.banking.service.impl;

import com.example.banking.dto.AccountDto;
import com.example.banking.entity.Account;
import com.example.banking.mapper.AccountMapper;
import com.example.banking.repository.AccountRepository;
import com.example.banking.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account= AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.maptoAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id){
        Account account=accountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exist"));
        return AccountMapper.maptoAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account=accountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exist"));
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }
        double total=account.getBalance()+amount;
        account.setBalance(total);
        Account savedAccount=accountRepository.save(account);
        return AccountMapper.maptoAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account=accountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exist"));
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be greater than zero");
        }
        if (amount > account.getBalance()){
            throw new IllegalArgumentException("Insufficient funds");
        }
        double total=account.getBalance()-amount;
        account.setBalance(total);
        Account savedAccount=accountRepository.save(account);
        return AccountMapper.maptoAccountDto(savedAccount);

    }

//    @Override
//    public AccountDto transfer(Long id1, Long id2, double amount) {
//        Account account1=accountRepository
//                .findById(id1)
//                .orElseThrow(()->new RuntimeException("Account does not exist"));
//        Account account2=accountRepository
//                .findById(id2)
//                .orElseThrow(()->new RuntimeException("Account does not exist"));
//        if (amount <= 0) {
//            throw new IllegalArgumentException("Transfer amount must be greater than zero");
//        }
//        double add=account2.getBalance()+amount;
//        double subtract=account1.getBalance()-amount;
//        account1.setBalance(subtract);
//        account2.setBalance(add);
//        Account savedAccount1=accountRepository.save(account1);
//        Account savedAccount2=accountRepository.save(account2);
//        return null;
//    }
}
