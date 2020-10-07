package com.ssafy.blockchallen.service.impl;

import com.ssafy.blockchallen.dto.findWalletDTO;
import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Wallet;
import com.ssafy.blockchallen.repository.AccountRepository;
import com.ssafy.blockchallen.repository.WalletRepository;
import com.ssafy.blockchallen.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService implements IWalletService {

   @Autowired
   private WalletRepository walletRepository;
   
   @Autowired
   private AccountRepository accountRepository;

   @Override
   public findWalletDTO findWallet(long id) {
	  Optional<Account> account = accountRepository.findById(id);
	  Optional<Wallet> wallet = walletRepository.findByAccount(account.get());
	  if(wallet.isPresent()) {
		  findWalletDTO retWallet = new findWalletDTO();
		  retWallet.setAddress(wallet.get().getAddress());
		  return retWallet;
	  }
	  else
		  return null;
   }

   @Override
   public Wallet create(long id, String address) {
      Wallet newWallet = new Wallet();
      Optional<Account> account = accountRepository.findById(id);
      newWallet.setAccount(account.get());
      newWallet.setAddress(address);
      return walletRepository.save(newWallet);
   }
   
}