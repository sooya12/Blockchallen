package com.ssafy.blockchallen.service;

import com.ssafy.blockchallen.dto.findWalletDTO;
import com.ssafy.blockchallen.entity.Wallet;

public interface IWalletService {
    findWalletDTO findWallet(long id);

    Wallet create(long id, String address);
}