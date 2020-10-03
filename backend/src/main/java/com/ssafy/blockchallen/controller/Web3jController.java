package com.ssafy.blockchallen.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/blockchallen")
public class Web3jController {

    @ApiOperation("송금")
    @PostMapping("/web3j/send")
    public void send() throws IOException, ExecutionException, InterruptedException {
        Admin admin = Admin.build(new HttpService("https://j3a102.p.ssafy.io/geth"));
        PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount("0x03fb923A157c20565E36D7d518418E1b9b0c2C86", "ssafy").sendAsync().get();

        BigInteger value = new BigInteger("1000000000000000000");
        BigInteger gasPrice = new BigInteger("10000000000000000");
        BigInteger gasLimit = new BigInteger("4700000");

        EthGetTransactionCount ethGetTransactionCount = admin.ethGetTransactionCount("0x03fb923A157c20565E36D7d518418E1b9b0c2C86", DefaultBlockParameterName.LATEST).sendAsync().get();

        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        Transaction transaction = Transaction.createEtherTransaction("0x03fb923A157c20565E36D7d518418E1b9b0c2C86", nonce, gasPrice, gasLimit, "0x401591416fb205B556A39CF989FD9Bbb497629aE", value);

        if(personalUnlockAccount.accountUnlocked()) {
            admin.personalSendTransaction(transaction, "ssafy").sendAsync().get();
            System.out.println("1EH 송금");
        }
    }
}
