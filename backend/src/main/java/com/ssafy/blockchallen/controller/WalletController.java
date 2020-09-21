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
   @RequestMapping(value = "/wallet/create", method = RequestMethod.PUT)
   public Object create(@RequestBody walletDTO wallet) {
      Wallet newWallet = walletService.create(wallet.getId(), wallet.getAddress());
      
      if(newWallet != null) {
         return new ResponseEntity<>(newWallet, HttpStatus.OK);
      } else {
         return new ResponseEntity<>("존재하지 않는 회원", HttpStatus.NO_CONTENT);
      }
   }
   
   // 지갑 주소로 굳이 조회를 해야할 필요가 있을까 하는 의구심에 주석처리를 해 놓았습니다.
//   @ApiOperation(value = "지갑 주소로 지갑 정보 찾기")
//   @RequestMapping(value = "/wallets/info/address", method = RequestMethod.GET)
//   public Wallet findAddress(@PathVariable String address) { // 지갑 주소 전달(address)
//      return walletService.findByWallet(wallet);
//   }

   @ApiOperation(value = "유저 아이디로 지갑 정보 찾기")
   @RequestMapping(value = "/wallet/{id}", method = RequestMethod.GET) //{id}로 받아와야 하려나?? 그런 느낌인거같소
   public Wallet findUserId(@PathVariable("id") long id) {
      return walletService.findById(id);
   }

   @ApiOperation(value = "Request ether")
   @RequestMapping(value ="/wallets/{address}", method = RequestMethod.PUT)
   public Wallet requestEth(@PathVariable String address){ // 테스트 가능하도록 일정 개수의 코인을 충전해준다.
      //Wallet newWallet = this.walletService.get(address);
      return null;
   }

}