package com.ssafy.blockchallen.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/blockchallen")
public class AccountController {
	
	private final String BACK_SERVER_URI = "http://localhost:8080";
	
	private final String kakaoRedirectBackURI = BACK_SERVER_URI + "/blockchallen/login";
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object login(HttpServletRequest request, HttpServletRequest response) {
		
		String code = request.getParameter("code");
		
		
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	// 로그인 토큰 가져오는 함수
	private String getKakaoAccessToken(String authorize_code) {
		String access_token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			
			// POST 요청을 위한 설정
			con.setRequestMethod("POST");
			con.setDoOutput(true); // 쓰는 기능 on
			
			// POST 요청에 필요한 파라미터를 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authrization_code");
			sb.append("&cliend_id=28c57e4dec8be27db1832926dba21bb0");
			sb.append("&redirect_uri=" + kakaoRedirectBackURI); // 로그인 함수로 리다이렉트 하게 해 줌
			sb.append("&code=" + authorize_code);
			sb.append("&client_secret=9hw1Iw1vK4fJtLTg2PQaWGeWVDZiMtgR");
			
			bw.write(sb.toString());
			bw.flush();
			
			int responseCode = con.getResponseCode(); // 성공 : 200
			System.out.println("토큰 얻기 성공? " + responseCode);
			
			// 요청을 통해 얻은 json 형식 response 읽기
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = "";
			String result = "";
			
			while((line=br.readLine()) != null) {
				result += null;
			}
			
			// gson 라이브러리의 클래스로 json 파싱 객체 생성
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			
			access_token = element.getAsJsonObject().get("access_token").getAsString();
	
			br.close();
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return access_token;
	}
	
	// 토큰으로 로그인 정보(이메일) 가져오는 함수
	private String getKakaoUserInfo(String access_token) {
		
		String userEmail = "";
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			
			// 요청에 필요한 header에 포함될 내용
			con.setRequestProperty("Authorizaion", "Bearer "+access_token);
	
			int responseCode = con.getResponseCode(); // 성공 : 200
			System.out.println("로그인 정보 얻기 성공? " + responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line = "";
			String result = "";
			
			while((line = br.readLine()) != null) {
				result += line;
			}
			
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			
			userEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().getAsJsonObject().get("email").getAsString();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return userEmail;
	}
	
}
