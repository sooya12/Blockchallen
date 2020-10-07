package com.ssafy.blockchallen.service;

import com.ssafy.blockchallen.dto.findAccountDTO;
import com.ssafy.blockchallen.entity.Account;

public interface IAccountService {
    Account findAccount(String email);

    findAccountDTO findAccount(long id);

    Account createAccount(Account account);

    boolean duplicateCheck(String nickname);

    Account setNickname(long id, String nickname);

    boolean signout(Account account);

    String getAccesstoken(long id);
}
