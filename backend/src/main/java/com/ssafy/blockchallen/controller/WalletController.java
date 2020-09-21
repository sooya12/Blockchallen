package com.ssafy.blockchallen.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.blockchallen.dto.findWalletDTO;
import com.ssafy.blockchallen.dto.walletDTO;
import com.ssafy.blockchallen.entity.Wallet;
import com.ssafy.blockchallen.service.IWalletService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/blockchallen")
public class WalletController {
   public static final Logger logger = LoggerFactory.getLogger(WalletController.class);

   @Autowired
   private IWalletService walletService; 

   @ApiOperation(value = "유저의 지갑 등록")
   @RequestMapping(value = "/wallet/create", method = RequestMethod.POST)
   public Object create(@RequestBody walletDTO wallet) {
      Wallet newWallet = walletService.create(wallet.getId(), wallet.getAddress());
      
      if(newWallet != null) {
         return new ResponseEntity<>(newWallet, HttpStatus.OK);
      } else {
         return new ResponseEntity<>("지갑 등록 실패", HttpStatus.NO_CONTENT);
      }
   }
   
   @ApiOperation(value = "유저 아이디로 지갑 정보 찾기")
   @RequestMapping(value = "/wallet/{id}", method = RequestMethod.GET)
   public Object findWallet(@PathVariable("id") long id) {
	   findWalletDTO wallet = walletService.findWallet(id);
	   if(wallet != null) {
		   return new ResponseEntity<>(wallet, HttpStatus.OK); 
	   }
	   else {
		   return new ResponseEntity<>("지갑 조회 실패", HttpStatus.NO_CONTENT); 
	   }
   }


}