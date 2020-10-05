package com.ssafy.blockchallen.controller;

import com.ssafy.blockchallen.dto.KakaoPayApprovalVO;
import com.ssafy.blockchallen.dto.KakaoPayReadyVO;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/blockchallen/kakaopay")
@Log
public class KakaoPayController {
    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;
    private String cid = "TC0ONETIME";
    private int pricePerEther = 1;
    private final String BACK_SERVER_URI = "http://localhost:8080/blockchallen";
	private final String FRONT_SERVER_URI = "http://localhost:3030";
//    private final String BACK_SERVER_URI = "https://j3a102.p.ssafy.io/blockchallen";
//    private final String FRONT_SERVER_URI = "https://j3a102.p.ssafy.io";

    @GetMapping("/ready")
    public Object kakaoPayReady(@RequestParam int ether) {

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "7856979edcbc8691503d0d88edb1ee50");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", cid);
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "BlockChallen");
        params.add("item_name", "블록챌린 이더");
        params.add("quantity", ether+"");
        params.add("total_amount", ether*pricePerEther+"");
        params.add("tax_free_amount", ether*pricePerEther+"");
        params.add("approval_url", FRONT_SERVER_URI+"/kakaoPaySuccess");
        params.add("cancel_url", FRONT_SERVER_URI+"/kakaoPayCancel");
        params.add("fail_url", FRONT_SERVER_URI+"/404");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);

            log.info("" + kakaoPayReadyVO);

            return ResponseEntity.ok().body(kakaoPayReadyVO);

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/kakaoPaySuccess")
    public Object kakaoPayInfo(@RequestParam String pg_token) {

        log.info("KakaoPayInfoVO............................................");
        log.info("-----------------------------");

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "7856979edcbc8691503d0d88edb1ee50");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", cid);
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "BlockChallen");
        params.add("pg_token", pg_token);


        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            log.info("" + kakaoPayApprovalVO);

            return ResponseEntity.ok().body(kakaoPayApprovalVO);

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }
}
