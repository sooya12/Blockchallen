package com.ssafy.blockchallen.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.service.IAccountService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/blockchallen")
public class AccountController {
	
	@Autowired
	private IAccountService accountService;
	
	private final String BACK_SERVER_URI = "http://localhost:8080";
	private final String FRONT_SERVER_URI = "http://localhost:3030";
	private final String kakaoRedirectBackURI = BACK_SERVER_URI + "/blockchallen/login";
	private final String kakaoRedirectFrontURI = FRONT_SERVER_URI + "/login";
//	private final String kakaoNicknameRedirectFrontURI = FRONT_SERVER_URI + "";
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public Object test() throws UnsupportedEncodingException {
		String client_id = "28c57e4dec8be27db1832926dba21bb0";
		String redirectURI = URLEncoder.encode(kakaoRedirectBackURI, "UTF-8");
		
		String apiURL = "https://kauth.kakao.com/oauth/authorize?";
		apiURL += "client_id=" + client_id;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&response_type=code";
		
		return new ResponseEntity<String>(apiURL, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Object login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String code = request.getParameter("code"); // authorize_code
		String access_token = getKakaoAccessToken(code); // 토큰 얻어오기
		
		// 이메일 정보 얻어오기
		String userEmail = getKakaoUserInfo(access_token);
		
		String token = "";
		
		Account account = null;

		System.out.println("login");

		if((account = accountService.findAccount(userEmail))!=null) {
			System.out.println("account");
			System.out.println(account.getId());
			// 토큰 대신 뭘로 리다이렉트 해야하지...? PK, email, 지갑? 사람 객체를 담아줘
			response.sendRedirect(kakaoRedirectFrontURI + "/" + account.getId());
			return new ResponseEntity<>("success",HttpStatus.OK);
		}
		
		response.sendRedirect(kakaoRedirectFrontURI + "/" + account.getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=28c57e4dec8be27db1832926dba21bb0");
			sb.append("&redirect_uri=" + kakaoRedirectBackURI); // 로그인 함수로 리다이렉트 하게 해 줌
			sb.append("&code=" + authorize_code);
			sb.append("&client_secret=9hw1Iw1vK4fJtLTg2PQaWGeWVDZiMtgR");
			
			bw.write(sb.toString());
			bw.flush();
			
			int responseCode = con.getResponseCode(); // 성공 : 200
			
			// 요청을 통해 얻은 json 형식 response 읽기
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = "";
			String result = "";
			
			while((line=br.readLine()) != null) {
				result += line;
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
			con.setRequestProperty("Authorization", "Bearer "+access_token);
	
			int responseCode = con.getResponseCode(); // 성공 : 200
			
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
