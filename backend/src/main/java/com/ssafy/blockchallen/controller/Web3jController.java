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

        String fromAddress = "0x37b6D2d7358c6eFA21684E713e84b20A3c61AE03";
        String fromPassword = "1234";
        String toAddress = "0x02C777293721d140EDecca8131D1b5ADD821b066";

        PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromAddress, fromPassword).sendAsync().get();

        BigInteger value = new BigInteger("1000000000000000000");
        BigInteger gasPrice = new BigInteger("10000000000000000");
        BigInteger gasLimit = new BigInteger("4700000");

        EthGetTransactionCount ethGetTransactionCount = admin.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();

        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        Transaction transaction = Transaction.createEtherTransaction(fromAddress, nonce, gasPrice, gasLimit, toAddress, value);

        if(personalUnlockAccount.accountUnlocked()) {
            admin.personalSendTransaction(transaction, fromPassword).sendAsync().get();
            System.out.println("1EH 송금");
        }
    }
}
