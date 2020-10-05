package com.ssafy.blockchallen.dto;

import lombok.Data;

import java.util.Date;

@Data
public class KakaoPayReadyVO {
    private String tid;
    private String next_redirect_pc_url;
    private String next_redirect_mobile_url;
    private Date created_at;
}
